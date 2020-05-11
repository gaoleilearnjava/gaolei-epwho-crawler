package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 企业数据以及爬取数据对比
 */
@Entity
@Table(name = "company_info_2")
@Data
public class CompanyInfo2 implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * pscode
     */
    @Id
    @Column(name = "pscode", insertable = false, nullable = false)
    private String pscode;

    /**
     * 未处理前的企业名称
     */
    @Column(name = "psname1")
    private String psname1;

    /**
     * 处理后的企业名称
     */
    @Column(name = "psname2")
    private String psname2;

    /**
     * 企业编码原始数据
     */
    @Column(name = "corporation_code")
    private String corporationCode;

    /**
     * 企业名称
     */
    @Column(name = "company_name", nullable = false)
    private String companyName;

    /**
     * 组织机构代码
     */
    @Column(name = "company_num", nullable = false)
    private String companyNum;

    /**
     * 企业所在行业
     */
    @Column(name = "company_industry")
    private String companyIndustry;

    /**
     * 企业所属地区
     */
    @Column(name = "company_area")
    private String companyArea;

    /**
     * 企业地址
     */
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 企业经营范围
     */
    @Column(name = "company_scope")
    private String companyScope;

    /**
     * 注册资本
     */
    @Column(name = "company_capital")
    private String companyCapital;

    /**
     * 实缴资本
     */
    @Column(name = "company_real_capital")
    private String companyRealCapital;

    /**
     * 经营状态
     */
    @Column(name = "company_status")
    private String companyStatus;

    /**
     * 成立时间
     */
    @Column(name = "company_established_time")
    private String companyEstablishedTime;

    /**
     * 统一社会信用代码
     */
    @Column(name = "company_social_credit_code", nullable = false)
    private String companySocialCreditCode;

    /**
     * 企业类型
     */
    @Column(name = "company_type")
    private String companyType;

    /**
     * 登记机关
     */
    @Column(name = "company_registration_authority")
    private String companyRegistrationAuthority;

    /**
     * 核准日期
     */
    @Column(name = "company_approval_date")
    private String companyApprovalDate;

    /**
     * 人员规模
     */
    @Column(name = "company_staff_size")
    private String companyStaffSize;

    /**
     * 营业期限
     */
    @Column(name = "company_operating_period")
    private String companyOperatingPeriod;

    /**
     * 参保人数
     */
    @Column(name = "company_number_of_participants")
    private String companyNumberOfParticipants;

    /**
     * 曾用名
     */
    @Column(name = "company_used_name")
    private String companyUsedName;


}