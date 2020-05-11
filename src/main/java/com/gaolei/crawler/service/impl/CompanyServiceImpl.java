package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.CompanyInfo2Repository;
import com.gaolei.crawler.dao.CompanyInfoRepository;
import com.gaolei.crawler.pojo.CompanyInfo;
import com.gaolei.crawler.pojo.CompanyInfo2;
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

    @Autowired
    private CompanyInfo2Repository companyDao2;

    @Override
    public void addCompanyInfo(List<CompanyInfo> companies) {
        companyDao.saveAll(companies);
    }

    @Override
    public void addOneCompany(CompanyInfo company) {
        companyDao.saveAndFlush(company);
    }

    @Override
    public void addOldCompany(String psCode, String psName1, String psName2, String corporationCode) {
        CompanyInfo2 company = new CompanyInfo2();
        company.setPscode(psCode);
        company.setPsname1(psName1);
        company.setPsname2(psName2);
        company.setCorporationCode(corporationCode);
        companyDao2.save(company);
    }

    @Override
    public void updateCompany(CompanyInfo2 companyInfo2) {
        companyDao2.saveAndFlush(companyInfo2);
    }

    @Override
    public CompanyInfo2 findPsCodeByOldName(String oldName) {
        return companyDao2.findByPsname2(oldName);
    }
}
