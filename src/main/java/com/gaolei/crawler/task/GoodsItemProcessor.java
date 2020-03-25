package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.GoodsItemPipeline;
import com.gaolei.crawler.pojo.GoodsItem;
import com.gaolei.crawler.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/14 12:40
 */
@Component
public class GoodsItemProcessor implements PageProcessor {

    @Autowired
    private GoodsItemPipeline goodsItemPipeline;

    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(0);   //设置睡眠时间0

    @Override
    public void process(Page page) {
        if (page.getUrl().get().contains("newtype")) {
            //如果是列表页，就获取每个三级分类的URL，并加入到队列中。
            System.out.println("是总列表");
            List<Selectable> firstItems = page.getHtml().xpath("//div[@class='evli_pro_list1']").nodes();
            for (Selectable firstItem : firstItems) {
                List<Selectable> secondItems = firstItem.xpath("//dd").nodes();
                for (Selectable secondItem : secondItems) {
                    List<Selectable> thirdItems = secondItem.xpath("//a").nodes();
                    for (Selectable thirdItem : thirdItems) {
                        String thirdItemUrl = thirdItem.xpath("//a/@href").get();
                        page.addTargetRequest(thirdItemUrl);
                    }

                }
            }
        } else if (page.getUrl().get().contains("chanpin")) {
            //如果已经是三级分类的详情页了，就遍历所有商品，存入数据，并把下一页加入到队列中。
            //先获取到三级分类名称

            System.out.println("是商品列表");


            List<GoodsItem> goodsItems = new ArrayList<>();

            String itemName = page.getHtml().xpath("//div[@class='Topline']//div[@class='p-sub-bav']//a[5]/text()").get();
            System.out.println("三级分类名称       " + itemName);

            //然后获取所有的商品名称
            //主BOX
            Selectable mainBox = page.getHtml().xpath("//div[@class='wrapper']//div[@class='proBox1']");
            if (mainBox != null) {
                List<Selectable> thirdItems = mainBox.xpath("//ul/li").nodes();
                if (thirdItems != null) {
                    for (Selectable thirdItem : thirdItems) {
                        String goodsName = thirdItem.xpath("//h3/a/text()").get().replaceAll("\\s", "");
                        System.out.println(goodsName);
                        GoodsItem goodsItem = new GoodsItem();
                        goodsItem.setGoodsId(UuidUtils.getUUID());
                        goodsItem.setGoodsName(goodsName);
                        goodsItem.setItemName(itemName);
                        goodsItems.add(goodsItem);
                    }
                }
            }


            //右box
            Selectable rightBox = page.getHtml().xpath("//div[@class='wrapper']//div[@class='proBoxRight']");
            if (rightBox != null) {
                List<Selectable> items = rightBox.xpath("//ul/li").nodes();
                if (items != null && items.size() != 0) {
                    for (Selectable item : items) {
                        String goodsName = item.xpath("//h2/a/text()").get().replaceAll("\\s", "");
                        System.out.println(goodsName);
                        GoodsItem goodsItem = new GoodsItem();
                        goodsItem.setGoodsId(UuidUtils.getUUID());
                        goodsItem.setGoodsName(goodsName);
                        goodsItem.setItemName(itemName);
                        goodsItems.add(goodsItem);
                    }
                }
            }
            //把下一页加入到队列中
            String nextUrl = page.getHtml().xpath("//div[@class='newspages']//a[@class='lt']/@href").get();
            System.out.println("获得的下一页地址是" + nextUrl);
            if (nextUrl != null && nextUrl.contains("chanpin")) {
                nextUrl = "http://www.hbzhan.com" + nextUrl;
                page.addTargetRequest(nextUrl);
                System.out.println("下一页是   " + nextUrl);
            } else {
                System.out.println("没有下一页");
            }
            page.putField("goodsItems", goodsItems);
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }


//    //定时任务,开始爬虫,每小时执行一次
//    @Scheduled(cron = "0 0 * * * ?")
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
//    public void process() {
//        String url = "http://www.hbzhan.com/product/newtype.html";
//        Spider.create(new GoodsItemProcessor())
//                .addUrl(url)//添加网址
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
//                .addPipeline(goodsItemPipeline)//添加pipeline
//                .thread(5)//设置多线程
//                .run();//启动
//    }

}
