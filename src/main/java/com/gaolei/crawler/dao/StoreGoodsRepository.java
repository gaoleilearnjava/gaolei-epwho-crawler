package com.gaolei.crawler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.gaolei.crawler.pojo.StoreGoods;

public interface StoreGoodsRepository extends JpaRepository<StoreGoods, String>, JpaSpecificationExecutor<StoreGoods> {

}