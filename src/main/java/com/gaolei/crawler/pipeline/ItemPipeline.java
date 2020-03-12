package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.StoreGoodsItem;
import com.gaolei.crawler.pojo.StoreLabel;
import com.gaolei.crawler.service.StoreGoodsItemService;
import com.gaolei.crawler.service.StoreLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 15:50
 */
@Component
public class ItemPipeline implements Pipeline {

    @Autowired
    private StoreGoodsItemService storeGoodsItemService;

    @Autowired
    private StoreLabelService storeLabelService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<StoreGoodsItem> items = resultItems.get("items");
        if (items != null) {
            items.forEach(storeGoodsItemService::addItem);
        }
        List<StoreLabel> labels = resultItems.get("labels");
        if (labels != null) {
            labels.forEach(storeLabelService::addLabel);
        }
    }
}
