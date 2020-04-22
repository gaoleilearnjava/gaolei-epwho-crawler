package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.CompanyInfo;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/27 9:57
 */
public interface CompanyService {
    void addCompanyInfo(List<CompanyInfo> companies);
    void addOneCompany(CompanyInfo company);
}
