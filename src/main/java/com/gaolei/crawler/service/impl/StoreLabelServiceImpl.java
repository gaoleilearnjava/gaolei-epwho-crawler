package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.StoreLabelDao;
import com.gaolei.crawler.pojo.StoreLabel;
import com.gaolei.crawler.service.StoreLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 17:29
 */
@Service
public class StoreLabelServiceImpl implements StoreLabelService {
    @Autowired
    private StoreLabelDao storeLabelDao;

    @Override
    public void addLabel(StoreLabel label) {
        storeLabelDao.saveAndFlush(label);
    }
}
