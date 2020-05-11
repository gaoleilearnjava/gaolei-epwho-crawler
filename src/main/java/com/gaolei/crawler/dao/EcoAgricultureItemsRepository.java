package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.EcoAgricultureItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EcoAgricultureItemsRepository extends JpaRepository<EcoAgricultureItems, Integer>, JpaSpecificationExecutor<EcoAgricultureItems> {

}