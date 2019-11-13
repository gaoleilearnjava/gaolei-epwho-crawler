package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.HbProduct;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:38
 */
public interface HbProductService {
    //增
    void addHbProduct(HbProduct product);
    //删
    void deleteHbProduct(HbProduct product);
    //改
    void updateHbProduct(HbProduct hbProduct);
    //查
    List<HbProduct> findHbProducts(HbProduct product);

}
