package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.CompanyPipeline;
import com.gaolei.crawler.pojo.CompanyInfo;
import com.gaolei.crawler.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 18:24
 */
@Component
public class CompanyProcessor implements PageProcessor {
    private List<String> nameList;
    private int start = 0;

    @Autowired
    private CompanyPipeline companyPipeline;

    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(5)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

    @Override
    public void process(Page page) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (page.getUrl().get().contains("key")) {
            //如果是列表页，获取列表页的第一个
            List<Selectable> list = page.getHtml().xpath("//div[@class='or_search_list']").nodes();
            if (list != null && list.size() != 0) {
                Selectable detail = list.get(0);
                String detailUrl = detail.xpath("//a[@class='or_look']/@href").get();
                System.out.println("详情地址为  " + detailUrl);
                page.addTargetRequest(page.getRequest().setUrl("https://shuidi.cn" + detailUrl));
            } else {
                start++;
                page.addTargetRequest(nameList.get(start));
            }
        } else if (page.getUrl().get().contains("company")) {
            //详情页就存起来
            CompanyInfo company = new CompanyInfo();
            //id
            company.setCompanyId(UuidUtils.getUUID());
            //名字
            String name = page.getHtml().xpath("//span[@class='company_name']/text()").get().replaceAll("\\s", "");
            System.out.println(name);
            company.setCompanyName(name);
            //组织机构代码
            String num = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[3]/td[4]/text()").get();
            System.out.println(num);
            company.setCompanyNum(num);
            //行业
            String industry = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[4]/td[2]/text()").get();
            System.out.println(industry);
            company.setCompanyIndustry(industry);
            //所属地区
            String area = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[5]/td[2]/text()").get();
            System.out.println(area);
            company.setCompanyArea(area);
            //企业地址
            String address = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[9]/td[2]/text()").get();
            System.out.println(address);
            company.setCompanyAddress(address);  //*[@id="m111"]/div/table[2]/tbody/tr[10]/td[2]/div/div[1]/div/text()
            //经营范围
            String scope = page.getHtml().xpath("//div[@class='overflow']/text()").nodes().get(0).get();
            System.out.println(scope);
            company.setCompanyScope(scope);
            //注册资本
            String capital = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[1]/td[2]/text()").get();
            System.out.println(capital);
            company.setCompanyCapital(capital);
            //实缴资本
            String realCapital = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[1]/td[4]/text()").get();
            System.out.println(realCapital);
            company.setCompanyCapital(realCapital);
            //经营状态
            String status = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[2]/td[2]/text()").get();
            System.out.println(status);
            company.setCompanyStatus(status);
            //成立时间
            String establishedDate = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[2]/td[4]/text()").get();
            System.out.println(establishedDate);
            company.setCompanyEstablishedTime(establishedDate);
            //统一社会信用代码
            String code = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[3]/td[2]/text()").get();
            System.out.println(code);
            company.setCompanySocialCreditCode(code);
            //企业类型
            String type = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[4]/td[4]/text()").get();
            System.out.println(type);
            company.setCompanyType(type);
            //登记机关
            String registration = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[5]/td[4]/text()").get();
            System.out.println(registration);
            company.setCompanyRegistrationAuthority(registration);
            //核准日期
            String approval = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[6]/td[2]/text()").get();
            System.out.println(approval);
            company.setCompanyApprovalDate(approval);
            //人员规模
            String staff = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[6]/td[4]/text()").get();
            System.out.println(staff);
            company.setCompanyStaffSize(staff);
            //营业期限
            String limit = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[7]/td[2]/text()").get();
            System.out.println(limit);
            company.setCompanyOperatingPeriod(limit);
            //参保人数
            String people = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[7]/td[4]/text()").get();
            System.out.println(people);
            company.setCompanyNumberOfParticipants(people);
            //曾用名
            String usedName = page.getHtml().xpath("//*[@id=\"m111\"]/div/table[2]/tbody/tr[8]/td[2]/text()").get();
            if (usedName.length() > 30) {
                usedName = usedName.substring(0, 30);
            }
            System.out.println(usedName);
            company.setCompanyUsedName(usedName);

            page.putField("company", company);
            start++;
            page.addTargetRequest(nameList.get(start));
        }
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
    public void process() throws IOException {


        File file = new File("C:\\Users\\17631\\Desktop\\list.txt");

//        File file = new File("C:\\product_list.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        //读取的原始文件每一行信息
        String line = "";
        int count = 0;
        List<String> nameList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            //对读取的每一行信息进行处理
            count++;
            line.split("\\s");

            String url = "https://shuidi.cn/b-search?key=" + URLEncoder.encode(line, StandardCharsets.UTF_8.name());
            System.out.println(count + "    " + url);
            nameList.add(url);
        }
        this.nameList = nameList;
        bufferedReader.close();
        Spider spider = Spider.create(this)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(companyPipeline)//添加pipeline
                .thread(1);//设置多线程
        spider.addUrl(this.nameList.get(0));
        spider.run();
    }
}

