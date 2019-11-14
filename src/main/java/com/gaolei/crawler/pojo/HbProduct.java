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
    /*
    * CREATE TABLE `hbzhan_store_products` (
  `product_id` varchar(32) NOT NULL COMMENT '主键ID',
  `product_name` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `product_pic1` varchar(200) DEFAULT NULL COMMENT '产品图片1',
  `product_pic2` varchar(200) DEFAULT NULL COMMENT '产品图片2',
  `product_pic3` varchar(200) DEFAULT NULL COMMENT '产品图片3',
  `product_pic4` varchar(200) DEFAULT NULL COMMENT '产品图片4',
  `product_pic5` varchar(200) DEFAULT NULL COMMENT '产品图片5',
  `product_type` varchar(50) DEFAULT NULL COMMENT '产品类型',
  `product_company` varchar(100) DEFAULT NULL COMMENT '所属企业',
  `company_id` varchar(32) DEFAULT NULL COMMENT '企业ID',
  `product_profile` text COMMENT '产品简介',
  `product_detail` longtext COMMENT '产品详情',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `product_model` varchar(20) DEFAULT NULL COMMENT '产品型号',
  `product_brand` varchar(100) DEFAULT NULL COMMENT '品牌',
  `product_min_ordered` varchar(50) DEFAULT NULL COMMENT '最小订货量',
  `company_type` varchar(50) DEFAULT NULL COMMENT '厂商性质',
  `product_location` varchar(20) DEFAULT NULL COMMENT '所在地',
  `product_price` varchar(255) DEFAULT NULL COMMENT '单价',
  `product_dtl_url` varchar(100) DEFAULT NULL COMMENT '产品详情链接',
  `product_source` int(11) DEFAULT NULL COMMENT '产品来源：0-第一环保网；1-环保网；2-中国网库'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4*/
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
