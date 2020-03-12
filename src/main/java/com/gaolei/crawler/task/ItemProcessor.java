package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.ItemPipeline;
import com.gaolei.crawler.pojo.StoreGoodsItem;
import com.gaolei.crawler.pojo.StoreLabel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 15:53
 */
@Component
public class ItemProcessor implements PageProcessor {
    @Autowired
    private ItemPipeline itemPipeline;

    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(0);   //设置睡眠时间0


    @Override
    public void process(Page page) {
        //新建一个条目列表
        List<StoreGoodsItem> items = new ArrayList<>();
        //新建一个标签列表
        List<StoreLabel> labels = new ArrayList<>();

        //获取一级条目
        List<Selectable> firstItems = page.getHtml().xpath("//div[@class='evli_body_top']").nodes();
        List<Selectable> secondItems = page.getHtml().xpath("//div[@class='evli_pro_list1']").nodes();

        for (int i = 0; i < firstItems.size(); i++) {
            Selectable firstItemInfo = firstItems.get(i);
            Selectable secondItemInfo = secondItems.get(i);
            //准备一级条目
            StoreGoodsItem firstItem = makeItem();
            String firstName = firstItemInfo.xpath("//h3/text()").get();
            firstItem.setItemName(firstName);
            firstItem.setItemFullName(firstName + "/");
            firstItem.setItemFatherId("0");
            items.add(firstItem);
            //准备一级条目对应下的二级条目
            List<Selectable> secondItemsOfFirstItem = secondItemInfo.xpath("//dt").nodes();
            List<Selectable> thirdItemsOfFirstItem = secondItemInfo.xpath("//dd").nodes();
            for (int j = 0; j < secondItemsOfFirstItem.size(); j++) {
                Selectable secondItemInfoOfFirstItem = secondItemsOfFirstItem.get(j);
                Selectable thirdItemsInfo = thirdItemsOfFirstItem.get(j);
                //生成二级条目
                StoreGoodsItem secondItem = makeItem();
                String secondName = secondItemInfoOfFirstItem.xpath("//a/text()").get();
                secondItem.setItemName(secondName);
                secondItem.setItemFatherId(firstItem.getItemId());
                secondItem.setItemFullName(firstName + "/" + secondName + "/");
                items.add(secondItem);
                //准备三级条目
                List<Selectable> thirdItems = thirdItemsInfo.xpath("//a").nodes();
                for (Selectable thirdItemInfo : thirdItems) {
                    StoreGoodsItem thirdItem = makeItem();
                    String thirdName = thirdItemInfo.xpath("//a/text()").get();
                    System.out.println("三级条目名称    " + thirdName);
                    thirdItem.setItemName(thirdName);
                    thirdItem.setItemFatherId(secondItem.getItemId());
                    thirdItem.setItemFullName(secondItem.getItemFullName() + thirdName + "/");
                    items.add(thirdItem);
                    StoreLabel label = new StoreLabel();
                    label.setLabelId(UuidUtils.getUUID());
                    label.setCreateTime(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()));
                    label.setLabelName(thirdName);
                    label.setLabelSort(999);
                    label.setStatus(1);
                    label.setCreateUser("0X000");
                    labels.add(label);
                }
            }

        }


        page.putField("items", items);
        page.putField("labels", labels);
    }


    public StoreGoodsItem makeItem() {
        StoreGoodsItem item = new StoreGoodsItem();
        //条目ID
        item.setItemId(UuidUtils.getUUID());

        //条目索引
        item.setItemIndex(1);


        //条目创建时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        item.setItemCreateTime(dateFormat.format(date));
        //条目状态，1是启用，9是禁用
        item.setItemStatus(1);
        return item;
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
        Spider.create(new ItemProcessor())
                .addUrl("http://www.hbzhan.com/product/newtype.html")//添加网址
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(itemPipeline)//添加pipeline
                .thread(1)//设置多线程
                .run();//启动
    }
}
