package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.ProductPipeline;
import com.gaolei.crawler.pojo.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 爬取第一环保网的环保产品
 *
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 14:36
 */
@Component
public class EpwhoProcessor implements PageProcessor {
    /**
     * 第一环保网的网址,所有信息都以它开头
     */
    private String url = "http://www.epwho.com";

    Logger logger = LoggerFactory.getLogger(EpwhoProcessor.class);

    @Autowired
    ProductPipeline productPipeline;
    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3);   //设置重试次数

    /**
     * 爬取页面列表主逻辑
     *
     * @param page
     */
    @Override
    public void process(Page page) {
        //解析页面,获取产品详情的url地址列表集合
        List<Selectable> nodes = page.getHtml().xpath("//ul[@class='mall_box2_ul']/li").nodes();
        //判断获取到的集合是否为空,如果为空,表示这是产品详情页
        if (nodes.size() != 0) {
            //如果不为空,表示这是列表页,挨个遍历每一个产品
            List<Product> products = new ArrayList<>();
            for (Selectable node : nodes) {
                //生成产品,随机生成产品ID,保存列表页的部分详情,产品详情的url一定要正确,后面需要根据url更新商品详情

                this.saveProduct(page, node, products);

            }
            page.putField("products", products);
            //获取下一页的url
            List<Selectable> nodess = page.getHtml().css("ul.mall_box2_ul div.nj-page > a", "href").nodes();
            String nextUrl = nodess.get(nodess.size() - 1).toString();
            if (!nextUrl.equals("/sell/index_1.html")) {
                //拼接网址前缀.
                String bkUrl = url + nextUrl;
                //将下一页的url添加到任务列表中
                page.addTargetRequest(bkUrl);
            }
        } else {
            //如果是商品详情页,进行商品详情页的处理
            saveProductDtl(page);
        }
    }

    /**
     * 保存产品列表页面可以看到的部分详情
     *
     * @param node 获取到的列表代码段
     */
    private void saveProduct(Page page, Selectable node, List<Product> products) {
        Product product = new Product();
        //获取产品图片
        String proPic = node.xpath("//div[1]/a/img/@src").toString();
        if (!proPic.startsWith(url)) {
            proPic = url + proPic;
        }
        product.setProduct_pic(proPic);
        //设置产品创建时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        String time = dateFormat.format(date);
        product.setCreate_time(time);
        // 获取产品名称
        String proName = node.xpath("//div[2]/h2/a/text()").toString();
        product.setProduct_name(proName);
        //根据产品名称设置产品ID
        String productId = UUID.nameUUIDFromBytes((proName).getBytes()).toString();
        product.setProduct_id(productId.replace("-", ""));
        // 获取产品详情地址
        String proDtlUrl = node.xpath("//div[2]/h2/a/@href").toString();
        product.setProduct_dtl_url(proDtlUrl);
        // 获取产品类别
        String proType = node.xpath("//div[2]/span/a/@title").toString();
        product.setProduct_type(proType);
        // 获取产品简介
        String profile = node.xpath("//div[2]/p/text()").toString();
        product.setProduct_profile(profile);
        // 获取厂家名称
        String factory = node.xpath("//div[3]/p/a/text()").toString();
        product.setProduct_company(factory);
        //根据厂家名称生成厂家ID
        product.setCompany_id(UUID.nameUUIDFromBytes((factory).getBytes()).toString().replace("-", ""));
        //设置产品详情状态为未爬取
        product.setProduct_dtl_status(0);
        //设置产品状态为在售
        product.setProduct_status(0);
        products.add(product);
        //将商品详情页加入爬取列表
        page.addTargetRequest(product.getProduct_dtl_url());
    }

    /**
     * 对商品详情进行更新保存,更新的依据是商品详情页的URL
     *
     * @param page
     */
    public void saveProductDtl(Page page) {

        Html html = page.getHtml();
        Selectable node = html.xpath("//td[@id='main']");//此node是我们主要要爬取的区域

        Product product = new Product();
        //设置商品详情URL
        product.setProduct_dtl_url(page.getUrl().toString());
        //设置产品详情状态为已爬取
        product.setProduct_dtl_status(1);
        //设置产品来源
        product.setProduct_source(0);
        //设置产品详情
        String detail = node.xpath("//div[5]/*[@class='content']").toString();
        product.setProduct_detail(detail);


        List<Selectable> list = page.getHtml().xpath("//*[@id=\"main\"]/div[3]/table/tbody/tr/td[3]/table/tbody/tr").nodes();
        for (Selectable s : list) {
            String name = s.xpath("//td[1]/text()").toString();

            if(name.startsWith("产品"))
            {
                String value = s.xpath("//td[2]/h1/text()").toString();
            }else{
                String value = s.xpath("//td[2]/text()").toString();
                if(name.startsWith("品牌")){
                    product.setProduct_brand(value);
                }else if(name.startsWith("单价")){
                    product.setProduct_price(value);
                }else if(name.startsWith("最小起订量")){
                    product.setProduct_min_ordered(value);
                }else if(name.startsWith("供货总量")){
                    product.setProduct_supply(value);
                }else if(name.startsWith("发货期限")){
                    value = s.xpath("//td[2]").toString().replaceAll("</?[^<]+>","").replaceAll("&nbsp;","").replaceAll(" ","");
                    product.setProduct_send_term(value);
                }else if(name.startsWith("有效期至")){
                    product.setProduct_valid_term(value);
                } else if (name.startsWith("最后更新")) {
                    product.setUpdate_time(value);
                }
            }
        }
        List<Product> products = new ArrayList<>();
        products.add(product);
        page.putField("products", products);
    }

    @Override
    public Site getSite() {
        return this.site;
    }

    //定时任务,开始爬虫,每小时执行一次
    //也可以改成用网页来执行的,使用controller
    //@Scheduled(cron = "0 0 * * * ?")
    //测试使用,立马执行
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void process() {
        logger.info("第一环保网-商城产品列表任务开始执行...");
        String url = "http://www.epwho.com/sell/index_109.html";
        Spider.create(new EpwhoProcessor())
                .addUrl(url)//添加网址
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(productPipeline)//添加pipeline
                .thread(2)//设置多线程
                .run();//启动
    }


}
