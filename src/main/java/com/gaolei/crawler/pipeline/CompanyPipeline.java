package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.CompanyInfo;
import com.gaolei.crawler.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 18:20
 */
@Component
public class CompanyPipeline implements Pipeline {
    @Autowired
    private CompanyService companyService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        CompanyInfo company = resultItems.get("company");
        if (company != null) {
            try {
                companyService.addOneCompany(company);
            } catch (Exception e) {
                System.out.println("公司重复，跳过");
            }
        }
    }
}
