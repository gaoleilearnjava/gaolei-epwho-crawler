package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.PolicyDao;
import com.gaolei.crawler.pojo.Policy;
import com.gaolei.crawler.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/2/27 15:49
 */
@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    private PolicyDao policyDao;

    /**
     * 新增政策法规
     *
     * @param policy
     */
    @Override
    @Transactional
    public void addPolicy(Policy policy) {
        policyDao.saveAndFlush(policy);
    }

    /**
     * 修改政策法规
     *
     * @param policy
     */
    @Override
    @Transactional
    public void modifyPolicy(Policy policy) {
        policyDao.saveAndFlush(policy);
    }

    /**
     * 删除政策法规
     *
     * @param policy
     */
    @Override
    @Transactional
    public void deletePolicy(Policy policy) {
        policyDao.delete(policy);
    }

    /**
     * 查询政策法规
     *
     * @param policy
     * @return
     */
    @Override
    public List<Policy> findPolicy(Policy policy) {
        return policyDao.findAll(Example.of(policy));
    }

    /**
     * 查询所有政策法规
     *
     * @param policy
     * @return
     */
    @Override
    public List<Policy> findAllPolicy(Policy policy) {
        return policyDao.findAll();
    }
}
