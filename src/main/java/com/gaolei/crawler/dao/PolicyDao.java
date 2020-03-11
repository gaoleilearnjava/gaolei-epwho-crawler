package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PolicyDao extends JpaRepository<Policy, String>, JpaSpecificationExecutor<Policy> {

}