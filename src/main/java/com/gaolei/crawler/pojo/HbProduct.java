package com.gaolei.crawler.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:22
 */
@Entity
@Table(name = "hbzhan_store_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HbProduct {
    @Id
    private String product_id;
    private String product_name;
    private String product_pic1;
    private String product_pic2;
    private String product_pic3;
    private String product_pic4;
    private String product_pic5;
    private String product_type;
    private String product_company;
    private String product_profile;
    private String product_detail;
    private String create_time;
    private String update_time;
    private String product_model;
    private String product_brand;
    private String product_min_ordered;
    private String company_type;
    private String product_location;
    private String product_price;
    private String product_dtl_url;
    private int product_source;
}
