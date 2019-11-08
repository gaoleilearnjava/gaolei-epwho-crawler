package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.Product;
import com.gaolei.crawler.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 15:11
 */
@Component
public class ProductPipeline implements Pipeline {
    @Autowired
    private ProductService productService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<Product> products = resultItems.get("products");
        if ((products != null) && (products.size() > 1)) products.forEach(productService::addProduct);
        else if ((products != null) && products.size() == 1) productService.updateProduct(products.get(0));
    }
}
