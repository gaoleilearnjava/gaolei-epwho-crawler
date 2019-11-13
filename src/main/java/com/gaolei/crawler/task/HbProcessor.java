package com.gaolei.crawler.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:35
 */
@Component
public class HbProcessor implements PageProcessor {
    Logger logger = LoggerFactory.getLogger(EpwhoProcessor.class);
    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3);   //设置重试次数


    @Override
    public void process(Page page) {

    }

    @Override
    public Site getSite() {
        return this.site;
    }


}
