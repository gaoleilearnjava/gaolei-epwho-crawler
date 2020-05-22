package com.gaolei.crawler.controller;

import com.gaolei.crawler.pipeline.EcoAgriculturePipeline;
import com.gaolei.crawler.task.EcoAgricultureProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

import java.io.IOException;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 14:15
 */
@RestController
@RequestMapping("/agriculture")
public class EcoAgricultureController {

    @Autowired
    private EcoAgriculturePipeline ecoAgriculturePipeline;

    @Autowired
    private EcoAgricultureProcessor ecoAgricultureProcessor;

    /**
     * 正式任务执行方法
     */
    @GetMapping("/start")
    public void process() throws IOException {
        Spider.create(ecoAgricultureProcessor)
                .setScheduler(new PriorityScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000_0)))
                .addPipeline(ecoAgriculturePipeline)
                .thread(1)
                .addUrl("https://www.cnhnb.com/p/sgzw/")
//                .addUrl("https://www.cnhnb.com/p/sczw/")
                .run();
    }
}
