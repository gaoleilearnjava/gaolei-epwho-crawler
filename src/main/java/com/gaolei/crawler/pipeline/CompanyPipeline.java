package com.gaolei.crawler.pipeline;

import com.gaolei.crawler.pojo.CompanyInfo2;
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
        CompanyInfo2 company = resultItems.get("company");
        if (company != null) {
            try {
                companyService.updateCompany(company);
            } catch (Exception e) {
                System.out.println("出问题啦！！！！原因如下");
                e.printStackTrace();
            }
        }
    }
}
