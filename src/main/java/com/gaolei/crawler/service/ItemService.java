package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.PachongItem;
import com.gaolei.crawler.pojo.StoreItem;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/13 18:08
 */
public interface ItemService {
    List<PachongItem> findAllSecondItem();

    void addStoreItems(List<StoreItem> storeItems);
}
