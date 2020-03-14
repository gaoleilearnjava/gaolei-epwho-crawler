package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StoreItemRepository extends JpaRepository<StoreItem, Void>, JpaSpecificationExecutor<StoreItem> {

}