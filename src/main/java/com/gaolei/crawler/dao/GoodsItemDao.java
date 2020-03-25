package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.GoodsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsItemDao extends JpaRepository<GoodsItem, String>, JpaSpecificationExecutor<GoodsItem> {

}