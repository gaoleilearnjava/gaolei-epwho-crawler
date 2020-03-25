package com.gaolei.crawler.controller;

import com.gaolei.crawler.pipeline.GoodsItemPipeline;
import com.gaolei.crawler.task.GoodsItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/14 13:40
 */
@RestController
@RequestMapping("/items")
public class GoodsItemController {

}
