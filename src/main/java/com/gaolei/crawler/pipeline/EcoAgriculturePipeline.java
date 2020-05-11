package com.gaolei.crawler.pipeline;


import com.gaolei.crawler.service.EcoAgricultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 13:56
 */
@Component
public class EcoAgriculturePipeline implements Pipeline {
    @Autowired
    private EcoAgricultureService ecoAgricultureService;

    @Override
    public void process(ResultItems resultItems, Task task) {

    }
}
