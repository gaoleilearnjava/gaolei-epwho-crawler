package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.HbProductPipeline;
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
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:35
 */
@Component
public class HbProcessor implements PageProcessor {
    @Autowired
    private HbProductPipeline hbProductPipeline;

    Logger logger = LoggerFactory.getLogger(EpwhoProcessor.class);
    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3);   //设置重试次数


    @Override
    public void process(Page page) {
        //获取产品种类的大列表
        List<Selectable> nodes = page.getHtml().xpath("//body/div[3]/div[@class=evli_pro_list1]").nodes();
        if (nodes.size() != 0) {
            //说明是种类列表页面
            for (Selectable node : nodes) {
                List<Selectable> types = node.xpath("/dl/dt").nodes();
                for (Selectable type : types) {
                    //获取分类名称
                    String typename = type.xpath("/a/text()").toString();
                    System.out.println(typename);
                    //获取该分类的商品列表网址
                    String productListHtml = type.xpath("/a/@href").toString();
                    //将分类的商品列表网址加入到爬取队列中
                    page.addTargetRequest(productListHtml);
                }
            }
        } else {
            //说明是产品列表页面或者是产品详情页面
            //判断是列表页还是产品详情页
            if (page.getUrl().toString().contains("chanpin")) {
                //产品主列表页
                Selectable productsNode = page.getHtml().xpath("//body/div[@class=wrapper]/proBox1");
                if (productsNode != null) {
                    //获取所有的产品列表
                    List<Selectable> productDtlLists = productsNode.xpath("//ul/li").nodes();
                    for (Selectable productDtl : productDtlLists) {
                        //获取到产品详情链接
                        String productDtlUrl = productDtl.xpath("//h3/a/@href").toString();
                        //将产品详情链接加入到爬取队列
                        page.addTargetRequest(productDtlUrl);
                    }
                }
                //视频产品列表页
                Selectable videoProductsNode = page.getHtml().xpath("//body/div[@class=wrapper]/proBoxRight");
                if (videoProductsNode != null) {
                    List<Selectable> videoProductDtlLists = videoProductsNode.xpath("//div[@class=proBoxRight]/ul/li").nodes();
                    for (Selectable videoProductDtl : videoProductDtlLists) {
                        String videoProductDtlUrl = videoProductDtl.xpath("//h2/a/@href").toString();
                        //将视频产品详情链接加入到爬取队列
                        page.addTargetRequest(videoProductDtlUrl);
                    }
                }

            } else {
                //产品详情页


            }
        }
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
        logger.info("环保在线-商城产品列表任务开始执行...");
        String url = "http://www.hbzhan.com/product/newtype.html";
        Spider.create(new EpwhoProcessor())
                .addUrl(url)//添加网址
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(hbProductPipeline)//添加pipeline
                .thread(3)//设置多线程
                .run();//启动
    }

}
