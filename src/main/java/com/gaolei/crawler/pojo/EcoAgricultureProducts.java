package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 农产品详情表
 */
@Entity
@Table(name = "eco_agriculture_products")
@Data
public class EcoAgricultureProducts implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, name = "product_id", nullable = false)
    private Integer productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name", nullable = false)
    private String productName;

    /**
     * 产品图片1
     */
    @Column(name = "product_img1")
    private String productImg1;

    /**
     * 产品图片2
     */
    @Column(name = "product_img2")
    private String productImg2;

    /**
     * 产品图片3
     */
    @Column(name = "product_img3")
    private String productImg3;

    /**
     * 产品图片4
     */
    @Column(name = "product_img4")
    private String productImg4;

    /**
     * 产品图片5
     */
    @Column(name = "product_img5")
    private String productImg5;

    /**
     * 产品属性
     */
    @Column(name = "product_attribute")
    private String productAttribute;

    /**
     * 产品详情
     */
    @Column(name = "product_word_detail")
    private String productWordDetail;

    /**
     * 产品发货地址
     */
    @Column(name = "product_address")
    private String productAddress;

    /**
     * 产品价格
     */
    @Column(name = "product_price")
    private String productPrice;

    /**
     * 产品规格1
     */
    @Column(name = "product_spec1")
    private String productSpec1;

    /**
     * 产品规格2
     */
    @Column(name = "product_spec2")
    private String productSpec2;

    /**
     * 产品规格3
     */
    @Column(name = "product_spec3")
    private String productSpec3;

    /**
     * 产品规格4
     */
    @Column(name = "product_spec4")
    private String productSpec4;

    /**
     * 产品规格5
     */
    @Column(name = "product_spec5")
    private String productSpec5;

    /**
     * 产品所属条目id
     */
    @Column(name = "product_item", nullable = false)
    private Integer productItem;

    /**
     * 产品所属的视频详情
     */
    @Column(name = "product_video_detail")
    private String productVideoDetail;

    /**
     * 产品所属的图片详情
     */
    @Column(name = "product_pic_detail")
    private String productPicDetail;

}