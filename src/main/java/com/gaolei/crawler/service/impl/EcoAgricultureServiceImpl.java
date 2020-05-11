package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.EcoAgricultureItemsRepository;
import com.gaolei.crawler.dao.EcoAgricultureProductsRepository;
import com.gaolei.crawler.pojo.EcoAgricultureItems;
import com.gaolei.crawler.pojo.EcoAgricultureProducts;
import com.gaolei.crawler.service.EcoAgricultureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 13:43
 */
@Service
@Transactional
public class EcoAgricultureServiceImpl implements EcoAgricultureService {

    @Autowired
    private EcoAgricultureProductsRepository ecoAgricultureProductsRepository;

    @Autowired
    private EcoAgricultureItemsRepository ecoAgricultureItemsRepository;

    @Override
    public void addItem(EcoAgricultureItems ecoAgricultureItems) {
        ecoAgricultureItemsRepository.saveAndFlush(ecoAgricultureItems);
    }

    @Override
    public void addProduct(EcoAgricultureProducts ecoAgricultureProducts) {
        ecoAgricultureProductsRepository.saveAndFlush(ecoAgricultureProducts);
    }
}
