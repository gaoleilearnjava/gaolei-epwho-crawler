package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.Product;

import java.util.List;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 16:05
 */
public interface ProductService {
    //增
    void addProduct(Product product);

    //根据信息查找符合条件的所有product
    List<Product> findProducts(Product product);

    //根据信息删除符合条件的product
    void deleteProduct(Product product);

    //改
    void updateProduct(Product product);

}
