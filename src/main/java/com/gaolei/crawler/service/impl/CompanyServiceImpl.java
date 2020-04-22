package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.CompanyInfoRepository;
import com.gaolei.crawler.pojo.CompanyInfo;
import com.gaolei.crawler.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/27 9:57
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyInfoRepository companyDao;

    @Override
    public void addCompanyInfo(List<CompanyInfo> companies) {
        companyDao.saveAll(companies);
    }

    @Override
    public void addOneCompany(CompanyInfo company) {
        companyDao.saveAndFlush(company);
    }
}
