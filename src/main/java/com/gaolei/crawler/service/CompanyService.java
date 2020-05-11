package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.CompanyInfo;
import com.gaolei.crawler.pojo.CompanyInfo2;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/27 9:57
 */
public interface CompanyService {
    void addCompanyInfo(List<CompanyInfo> companies);

    void addOneCompany(CompanyInfo company);

    void addOldCompany(String psCode, String psName1, String psName2, String corporationCode);

    void updateCompany(CompanyInfo2 companyInfo2);

    CompanyInfo2 findPsCodeByOldName(String oldName);
}
