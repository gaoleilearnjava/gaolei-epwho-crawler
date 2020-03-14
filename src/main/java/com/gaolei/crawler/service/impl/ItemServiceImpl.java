package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.PachongItemRepository;
import com.gaolei.crawler.dao.StoreItemRepository;
import com.gaolei.crawler.pojo.PachongItem;
import com.gaolei.crawler.pojo.StoreItem;
import com.gaolei.crawler.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/13 18:09
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private PachongItemRepository pachongItemDao;

    @Autowired
    private StoreItemRepository storeItemDao;


    @Override
    public List<PachongItem> findAllSecondItem() {
        return pachongItemDao.findAllByItemGeneration(2);
    }

    @Override
    public void addStoreItems(List<StoreItem> storeItems) {
        storeItemDao.saveAll(storeItems);
    }
}
