package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.StoreGoodsItemDao;
import com.gaolei.crawler.pojo.StoreGoodsItem;
import com.gaolei.crawler.service.StoreGoodsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 15:42
 */
@Service
public class StoreGoodsItemServiceImpl implements StoreGoodsItemService {
    @Autowired
    private StoreGoodsItemDao storeGoodsItemDao;

    @Override
    public void addItem(StoreGoodsItem item) {
        storeGoodsItemDao.saveAndFlush(item);
    }

    @Override
    public void modifyItem(StoreGoodsItem item) {
        storeGoodsItemDao.saveAndFlush(item);
    }

    @Override
    public void deleteItem(StoreGoodsItem item) {
        storeGoodsItemDao.delete(item);
    }

    @Override
    public List<StoreGoodsItem> findItem(StoreGoodsItem item) {
        return storeGoodsItemDao.findAll(Example.of(item));
    }

    @Override
    public List<StoreGoodsItem> findAllItems(StoreGoodsItem item) {
        return storeGoodsItemDao.findAll();
    }
}
