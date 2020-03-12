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

        //把一二三级分类都插入到条目表里面，
        //先爬取一级条目，获取到一级条目的所有二级条目。
        //然后获取二级条目下的所有三级条目。
        //单独把三级分类插入到标签表里面
        StoreGoodsItem item = new StoreGoodsItem();
        //条目ID
        item.setItemId(UuidUtils.getUUID());


        //条目名字


        //父条目ID


        //条目全名（从父条目名字开始）


        //条目索引
        item.setItemIndex(1);


        //条目创建时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        item.setItemCreateTime(dateFormat.format(date));
        //条目状态，1是启用，9是禁用
        item.setItemStatus(1);


        page.putField("items", items);
        page.putField("labels", labels);
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
