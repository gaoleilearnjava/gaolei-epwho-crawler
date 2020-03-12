package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.StoreGoodsItem;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 15:40
 */
public interface StoreGoodsItemService {
    /**
     * 增
     *
     * @param item
     */
    void addItem(StoreGoodsItem item);

    /**
     * 改
     *
     * @param item
     */
    void modifyItem(StoreGoodsItem item);

    /**
     * 删
     *
     * @param item
     */
    void deleteItem(StoreGoodsItem item);

    /**
     * 查
     *
     * @param item
     * @return
     */
    List<StoreGoodsItem> findItem(StoreGoodsItem item);

    /**
     * 查询所有
     *
     * @param item
     * @return
     */
    List<StoreGoodsItem> findAllItems(StoreGoodsItem item);
}
