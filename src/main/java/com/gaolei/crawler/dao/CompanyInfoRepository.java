package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, String>, JpaSpecificationExecutor<CompanyInfo> {

}