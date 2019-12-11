package com.gaolei.crawler.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.gaolei.crawler.pojo.StoreGoodsDetailsInfo;

public interface StoreGoodsDetailsInfoRepository extends JpaRepository<StoreGoodsDetailsInfo, String>, JpaSpecificationExecutor<StoreGoodsDetailsInfo> {

}