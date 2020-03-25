package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 商品名称-三级分类  对应表
 */
@Table(name = "goods_name_item_name")
@Data
@Entity
public class GoodsItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @Id
    @Column(name = "goods_id", insertable = false, nullable = false)
    private String goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 三级分类名称
     */
    @Column(name = "item_name")
    private String itemName;

    
}