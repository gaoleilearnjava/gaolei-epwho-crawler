package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.GoodsItem;
import com.gaolei.crawler.service.GoodsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/14 12:35
 */
@Component
public class GoodsItemPipeline implements Pipeline {
    @Autowired
    private GoodsItemService goodsItemService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<GoodsItem> goodsItems = resultItems.get("goodsItems");
        if (goodsItems != null && goodsItems.size() != 0) {
            goodsItemService.addGoodsItem(goodsItems);
        }
    }
}
