package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.Policy;
import com.gaolei.crawler.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/2/27 15:43
 */
@Component
public class PolicyPipeline implements Pipeline {
    @Autowired
    private PolicyService policyService;


    @Override
    public void process(ResultItems resultItems, Task task) {
        Policy policy = resultItems.get("policy");
        if (policy != null) {
            policyService.addPolicy(policy);
        }
        List<Policy> policies = resultItems.get("policies");
        if (policies != null) policies.forEach(policyService::addPolicy);
    }
}
