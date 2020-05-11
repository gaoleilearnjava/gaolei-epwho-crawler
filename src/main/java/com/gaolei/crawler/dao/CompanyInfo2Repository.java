package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.CompanyInfo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyInfo2Repository extends JpaRepository<CompanyInfo2, String>, JpaSpecificationExecutor<CompanyInfo2> {
    CompanyInfo2 findByPsname2(String paname2);
}