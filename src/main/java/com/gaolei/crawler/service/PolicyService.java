package com.gaolei.crawler.service;

import com.gaolei.crawler.pojo.Policy;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/2/27 15:43
 */
public interface PolicyService {
    /**
     * 增
     *
     * @param policy
     */
    void addPolicy(Policy policy);

    /**
     * 改
     *
     * @param policy
     */
    void modifyPolicy(Policy policy);

    /**
     * 删
     *
     * @param policy
     */
    void deletePolicy(Policy policy);

    /**
     * 查
     *
     * @param policy
     * @return
     */
    List<Policy> findPolicy(Policy policy);

    /**
     * 查询所有
     *
     * @param policy
     * @return
     */
    List<Policy> findAllPolicy(Policy policy);
}
