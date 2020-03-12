package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.StoreLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StoreLabelDao extends JpaRepository<StoreLabel, Void>, JpaSpecificationExecutor<StoreLabel> {

}