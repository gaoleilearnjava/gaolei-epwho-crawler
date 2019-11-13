package com.gaolei.crawler.pipeline;


import com.gaolei.crawler.pojo.HbProduct;
import com.gaolei.crawler.service.impl.HbProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:37
 */
@Component
public class HbProductPipeline implements Pipeline {
    @Autowired
    private HbProductServiceImpl hbProductService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        HbProduct product = resultItems.get("product");
        if (product != null)
            hbProductService.addHbProduct(product);
    }
}
