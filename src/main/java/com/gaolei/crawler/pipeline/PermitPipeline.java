package com.gaolei.crawler.pipeline;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/25 11:32
 */
@Component
public class PermitPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println();
    }
}
