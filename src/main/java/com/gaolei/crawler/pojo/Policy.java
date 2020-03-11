package com.gaolei.crawler.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "hospital_policy")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Policy implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 政策法规ID
     */
    @Id
    @Column(name = "policy_id", nullable = false)
    private String policyId;

    /**
     * 政策法规名称
     */
    @Column(name = "policy_name")
    private String policyName;

    /**
     * 政策法规摘要
     */
    @Column(name = "policy_profile")
    private String policyProfile;

    /**
     * 政策法规详情
     */
    @Column(name = "policy_detail")
    private String policyDetail;

    /**
     * 政策法规来源
     */
    @Column(name = "policy_source")
    private String policySource;

    /**
     * 政策法规创建时间
     */
    @Column(name = "policy_create_time")
    private String policyCreateTime;

    /**
     * 政策法规创建用户id
     */
    @Column(name = "policy_create_user")
    private String policyCreateUser;

    /**
     * 政策法规更新时间
     */
    @Column(name = "policy_update_time")
    private String policyUpdateTime;

    /**
     * 政策法规更新用户id
     */
    @Column(name = "policy_update_user")
    private String policyUpdateUser;

    /**
     * 政策法规状态（0禁用，1启用）
     */
    @Column(name = "policy_status")
    private Integer policyStatus;

    /**
     * 政策法规排序数
     */
    @Column(name = "policy_sort")
    private Integer policySort;

    /**
     * 政策法规图片ID
     */
    @Column(name = "policy_pic")
    private String policyPic;

    /**
     * 政策法规阅读数
     */
    @Column(name = "policy_read_count")
    private Integer policyReadCount;


}