package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.EcoAgricultureProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EcoAgricultureProductsRepository extends JpaRepository<EcoAgricultureProducts, Integer>, JpaSpecificationExecutor<EcoAgricultureProducts> {

}