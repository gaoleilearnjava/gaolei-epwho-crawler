package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.HbProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:30
 */
public interface HbProductDao extends JpaRepository<HbProduct, String>, JpaSpecificationExecutor<HbProduct> {
}
