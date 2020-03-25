package com.gaolei.crawler.pojo;

import lombok.Data;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/25 14:44
 */
@Data
public class Permit {
    private String Province;
    private String location;
    private String permitNum;
    private String companyName;
    private String companyType;
    private String validDate;
    private String permitDate;
    private String companyUrl;
}
