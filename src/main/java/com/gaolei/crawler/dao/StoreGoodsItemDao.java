package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.StoreGoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StoreGoodsItemDao extends JpaRepository<StoreGoodsItem, String>, JpaSpecificationExecutor<StoreGoodsItem> {

}