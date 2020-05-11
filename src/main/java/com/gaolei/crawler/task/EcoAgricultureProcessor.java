package com.gaolei.crawler.task;

import com.gaolei.crawler.service.EcoAgricultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 13:45
 */
@Component
public class EcoAgricultureProcessor implements PageProcessor {
    @Autowired
    private EcoAgricultureService ecoAgricultureService;


    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setRetryTimes(0)//设置重试次数
            .setSleepTime(5)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");


    @Override
    public void process(Page page) {
        //一开始是目录页

    }

    @Override
    public Site getSite() {
        return this.site;
    }


}
