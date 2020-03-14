package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Table(name = "store_goods_item_info")
@Entity
public class PachongItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_full_name")
    private String itemFullName;

    @Column(name = "item_father_id")
    private String itemFatherId;

    @Column(name = "item_create_time")
    private String itemCreateTime;

    /**
     * 爷爷条目的ID
     */
    @Column(name = "item_grand_father_id")
    private String itemGrandFatherId;

    /**
     * 1一级条目 2二级条目 3三级条目
     */
    @Column(name = "item_generation")
    private Integer itemGeneration;


}