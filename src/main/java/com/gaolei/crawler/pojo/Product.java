package com.gaolei.crawler.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 15:24
 */
@Entity
@Table(name = "store_epwho_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    /*CREATE TABLE `store_epwho_product` (
  `product_id` varchar(32) NOT NULL COMMENT '主键ID',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
   `product_pic` varchar(200) DEFAULT NULL COMMENT '商品图片',
  `product_type` varchar(50) DEFAULT NULL COMMENT '商品类型',
  `product_company` varchar(100) DEFAULT NULL COMMENT '所属企业',
  `product_profile` text COMMENT '商品简介',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `product_status` int(11) DEFAULT NULL COMMENT '产品状态：0-在售;1-下架',
  `product_dtl_url` varchar(100) DEFAULT NULL COMMENT '产品详情连接',
  `product_dtl_status` int(11) DEFAULT NULL COMMENT '详情状态：0-未爬取;1-已爬取',
  `product_brand` varchar(100) DEFAULT NULL COMMENT '品牌',
  `product_detail` longtext COMMENT '详情',
  `product_price` varchar(255) DEFAULT NULL COMMENT '单价',
  `product_min_ordered` varchar(50) DEFAULT NULL COMMENT '最小起订量',
  `product_supply` varchar(50) DEFAULT NULL COMMENT '供货总量',
  `product_send_term` varchar(100) DEFAULT NULL COMMENT '发货期限',
  `product_valid_term` varchar(100) DEFAULT NULL COMMENT '有效期至',
  `update_user` varchar(32) DEFAULT NULL COMMENT '变更人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `product_source` int(11) DEFAULT NULL COMMENT '产品来源：0-第一环保网；1-环保网；2-中国网库',
  `product_img` varchar(32) DEFAULT NULL COMMENT '产品本地图片',
  `product_deal_status` int(11) DEFAULT NULL COMMENT '处理状态：0-未同步；1-已同步',
  `product_item` varchar(32) DEFAULT NULL COMMENT '产品分类',
  `company_id` varchar(32) DEFAULT NULL COMMENT '企业ID',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4*/

    @Id
    //产品ID(这个用UUID生成)
    private String product_id;
    //公司ID
    private String company_id;
    //产品名称
    private String product_name;
    //产品图片
    private String product_pic;
    //产品类型
    private String product_type;
    //产品生产厂家
    private String product_company;
    //产品概述
    private String product_profile;
    //创建时间
    private String create_time;
    //'产品状态：0-在售;1-下架'
    private int product_status;
    //产品详情链接
    private String product_dtl_url;
    //详情状态：0-未爬取;1-已爬取',
    private int product_dtl_status;
    //产品品牌
    private String product_brand;
    //产品详情
    private String product_detail;
    //产品价格
    private String product_price;
    //产品最小订购量
    private String product_min_ordered;
    //产品供货量
    private String product_supply;
    //产品发货期限
    private String product_send_term;
    //产品有效期
    private String product_valid_term;
    //产品更新时间
    private String update_time;
    //产品来源
    private int product_source;

    //update_user(变更人,变更的时候用的)
    // product_img('产品本地图片')
    // product_deal_status('处理状态：0-未同步；1-已同步')


    // product_item(产品分类)
    private String product_item;
}
