package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.EcoAgricultureItems;
import com.gaolei.crawler.pojo.EcoAgricultureProducts;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 13:42
 */
public interface EcoAgricultureService {
    void addItem(EcoAgricultureItems ecoAgricultureItems);

    int getItemIdByItemName(String itemName);

    void addProduct(EcoAgricultureProducts ecoAgricultureProducts);
}
