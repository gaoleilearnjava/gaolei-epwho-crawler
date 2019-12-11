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
