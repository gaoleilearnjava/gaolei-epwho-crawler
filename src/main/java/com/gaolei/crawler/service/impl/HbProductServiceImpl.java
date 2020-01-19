package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.HbProductDao;
import com.gaolei.crawler.pojo.HbProduct;
import com.gaolei.crawler.service.HbProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:46
 */
@Service
public class HbProductServiceImpl implements HbProductService {
    @Autowired
    private HbProductDao hbProductDao;

    @Override
    public void addHbProduct(HbProduct product) {
        hbProductDao.saveAndFlush(product);
    }

    @Override
    public void deleteHbProduct(HbProduct product) {
        hbProductDao.delete(product);
    }

    @Override
    public void updateHbProduct(HbProduct hbProduct) {
        hbProductDao.save(hbProduct);
    }

    @Override
    public List<HbProduct> findHbProducts(HbProduct product) {
        Example<HbProduct> example = Example.of(product);
        List<HbProduct> result = hbProductDao.findAll(example);
        return result;
    }

    public List<HbProduct> findAllHbProducts() {
        List<HbProduct> hbProductList = hbProductDao.findAll();
        return hbProductList;
    }
}
