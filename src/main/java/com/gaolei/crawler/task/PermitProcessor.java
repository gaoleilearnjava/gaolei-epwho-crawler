package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.PermitPipeline;
import com.gaolei.crawler.pojo.Permit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.io.*;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/25 11:34
 */
public class PermitProcessor implements PageProcessor {
    @Autowired
    private PermitPipeline permitPipeline;


    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(5);


    @Override
    public void process(Page page) {
        System.setProperty("webdriver.chrome.driver",
                "C://chromedriver.exe");
        // 第二步：初始化驱动
        WebDriver driver = new ChromeDriver();
        // 第三步：获取目标网页
        driver.get("http://permit.mee.gov.cn/permitExt/syssb/xkgg/xkgg!licenseInformation.action");

        for (int i = 2; i <= 5; i++) {
            List<WebElement> detailUrls = driver.findElements(By.xpath("//td[@class='bgcolor1']/a"));
            for (WebElement detailUrl : detailUrls) {
                System.out.println(detailUrl.getAttribute("href"));
            }
            // 第四步：解析。以下就可以进行解了。使用webMagic、jsoup等进行必要的解析
            WebElement nextPage = driver.findElement(By.xpath("//div[@class='fr margin-t-33 margin-b-20']/a[@onclick='javascript:jumpPage2(" + i + ")']"));
            nextPage.click();
        }
        driver.close();
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
        Spider.create(new PermitProcessor())
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(permitPipeline)//添加pipeline
                .thread(1).run();
    }





}
