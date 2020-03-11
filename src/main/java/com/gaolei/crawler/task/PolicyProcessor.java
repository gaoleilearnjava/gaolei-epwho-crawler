package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.PolicyPipeline;
import com.gaolei.crawler.pojo.Policy;
import com.gaolei.crawler.util.DownloadAndUploadUtils;
import com.gaolei.crawler.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/2/27 15:52
 */
@Component
public class PolicyProcessor implements PageProcessor {
    @Autowired
    private PolicyPipeline policyPipeline;

    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(0);   //设置睡眠时间0


    @Override
    public void process(Page page) {
        List<Selectable> policyList = page.getHtml().xpath("//div[@class='bd mobile_list']/div/ul/li").nodes();
        //如果是各个分类列表，就点进法律详情。并将下一页加入到爬取列表
        if (policyList.size() != 0) {
            List<Policy> policies = new ArrayList<>();
            for (Selectable policyDetailUrl : policyList) {
                //获取到详情页的后缀
                String lastName = policyDetailUrl.xpath("//a/@href").get();
                if (lastName.startsWith("./")) {
                    //点开头的是第一种
                    lastName = lastName.substring(2);
                    //和地址前缀组合成详情页地址
                    String policyUrl = "http://www.mee.gov.cn/ywgz/fgbz/sthjshpczd/" + lastName;
                    //将详情页地址加入到爬取列表中
                    page.addTargetRequest(policyUrl);
                } else if (lastName.endsWith("pdf") || lastName.endsWith("doc")) {
                    Policy policy = new Policy();
                    //设置id
                    policy.setPolicyId(UuidUtils.getUUID());
                    //设置初始阅读数为0
                    policy.setPolicyReadCount(0);
                    //设置法规来源
                    policy.setPolicySource("中华人民共和国生态环境部");
                    //设置创建用户
                    policy.setPolicyCreateUser("9374727572644bb98065e9e41075269e");
                    //设置法规状态
                    policy.setPolicyStatus(1);
                    //设置法规排序数
                    policy.setPolicySort(10000);
                    // 政策法规名称
                    String name = policyDetailUrl.xpath("//a/text()").get();
                    System.out.println("文件详情，名字为*************" + name);
                    policy.setPolicyName(name);
                    //设置创建时间
                    String date = policyDetailUrl.xpath("//span[@class=date]/text()").get() + " 00:00:00";
                    policy.setPolicyCreateTime(date);
                    //政策法规详情，直接下载文件，然后上传到服务器，并将fast dfs的地址加密后存到详情里
                    try {
                        String fastdfsUrl = DownloadAndUploadUtils.downloadFile(lastName);
                        String detail = new String(Base64Utils.encode(fastdfsUrl.getBytes()));
                        policy.setPolicyDetail(detail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("下载上传文件失败");
                    }
                    policies.add(policy);
                    System.out.println(name + "**************存进去了");
                } else if (lastName.startsWith("../")) {
                    //政府信息公开详情
                    String policyUrl = "http://www.mee.gov.cn/" + lastName.substring(9);
                    page.addTargetRequest(policyUrl);
                } else {
                    page.addTargetRequest(lastName);
                }
            }
            page.putField("policies", policies);
        } else {
            String url = page.getUrl().get();
            if (url.startsWith("http://www.mee.gov.cn/ywgz/fgbz/")) {
                firstDetail(page);
            } else {
                secondDetail(page);
            }
        }
    }

    /**
     * 处理标准详情
     *
     * @param page
     */
    public void firstDetail(Page page) {
        //如果是详情页，直接新建政策法规，爬取内容，存入数据库。
        //新建一个政策法规
        Policy policy = new Policy();
        //随机生成id
        String uuid = UuidUtils.getUUID();
        policy.setPolicyId(uuid);
        //设置初始阅读数为0
        policy.setPolicyReadCount(0);
        //设置法规来源
        policy.setPolicySource("中华人民共和国生态环境部");
        //设置创建用户
        policy.setPolicyCreateUser("9374727572644bb98065e9e41075269e");
        //设置法规状态
        policy.setPolicyStatus(1);
        //设置法规排序数
        policy.setPolicySort(10000);
        // 政策法规名称
        String name = page.getHtml().xpath("//body/div[@class='innerBg']//h2[@class='neiright_Title']/text()").get();
        System.out.println("标准详情*****************" + name);
        policy.setPolicyName(name);
        //设置创建时间
        String time = page.getHtml().xpath("//div[@class='neiright_JPZ_GK']/span[@class='xqLyPc time']/text()").get() + " 00:00:00";
        policy.setPolicyCreateTime(time);
        //政策法规详情
        String detail = "";
        List<Selectable> details = page.getHtml().xpath("//div[@class='neiright_JPZ_GK_CP']//p").nodes();
        for (Selectable d : details) {
            detail += d.get();
        }
        //政策法规摘要
        //摘要就是详情去掉标签后的前100个字
        String profile = detail.replaceAll("<.*?>", "").replaceAll("&nbsp;", "").replaceAll("\\s", "");
        if (profile.length() > 300) {
            profile = profile.substring(0, 300);
        }
        policy.setPolicyProfile(profile);
//            //加密详情
        detail = new String(Base64Utils.encode(detail.getBytes()));
        policy.setPolicyDetail(detail);
        //交给pipeline，存到数据库中
        page.putField("policy", policy);
    }

    /**
     * 处理政府信息公开详情
     *
     * @param page
     */
    public void secondDetail(Page page) {
        //新建一个政策法规
        Policy policy = new Policy();
        //随机生成id
        String uuid = UuidUtils.getUUID();
        policy.setPolicyId(uuid);
        //设置初始阅读数为0
        policy.setPolicyReadCount(0);
        //设置创建用户
        policy.setPolicyCreateUser("9374727572644bb98065e9e41075269e");
        //设置法规状态
        policy.setPolicyStatus(1);
        //设置法规排序数
        policy.setPolicySort(10000);

        Selectable head = page.getHtml().xpath("//body/div[2]/div[1]/div[1]/div[1]/ul");
        // 政策法规名称
        String name = head.xpath("//li[1]//p/text()").get();
        System.out.println("政府信息公开***************" + name);
        policy.setPolicyName(name);
        //设置创建时间
        String time = head.xpath("//li[3]/div[2]/text()").get();
        System.out.println("****************" + time + "*********************");
        policy.setPolicyCreateTime(time);
        //设置法规来源
        String source = head.xpath("//li[3]/div[1]/i/text()").get();
        if (source == null) {
            source = head.xpath("//li[3]/div[1]/text()").get();
        }
        if (source == null) {
            source = "中华人民共和国生态环境部";
        }
        policy.setPolicySource(source);

        //政策法规详情
        List<Selectable> detailNodes = page.getHtml().xpath("//body/div[2]/div[2]/div[@id='print_html']/div").nodes();
        String detail = "";
        for (Selectable detailNode : detailNodes) {
            detail += detailNode.get();
        }
        //政策法规摘要
        String profile = detail.replaceAll("<.*?>", "").replaceAll("&nbsp;", " ").replaceAll("\\s", "");
        if (profile.length() >= 301) {
            profile = profile.substring(0, 300);
        }
        policy.setPolicyProfile(profile);

        //加密详情
        detail = new String(Base64Utils.encode(detail.getBytes()));
        policy.setPolicyDetail(detail);

        //交给pipeline，存到数据库中
        page.putField("policy", policy);
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    /**
     * 正式任务执行方法
     */
    //定时任务,开始爬虫,每小时执行一次
    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void process() {
        Spider.create(new PolicyProcessor())
                .addUrl("http://www.mee.gov.cn/ywgz/fgbz/sthjshpczd/")//添加网址
                .addUrl("http://www.mee.gov.cn/ywgz/fgbz/sthjshpczd/index_1.shtml")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(policyPipeline)//添加pipeline
                .thread(1)//设置多线程
                .run();//启动
    }

}
