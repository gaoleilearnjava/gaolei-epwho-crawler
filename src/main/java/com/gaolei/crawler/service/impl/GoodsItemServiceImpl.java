package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.GoodsItemDao;
import com.gaolei.crawler.pojo.GoodsItem;
import com.gaolei.crawler.service.GoodsItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/14 12:37
 */
@Service
@Transactional
public class GoodsItemServiceImpl implements GoodsItemService {
    @Autowired
    private GoodsItemDao goodsItemDao;

    @Override
    public void addGoodsItem(List<GoodsItem> goodsItems) {
        goodsItemDao.saveAll(goodsItems);
    }
}
