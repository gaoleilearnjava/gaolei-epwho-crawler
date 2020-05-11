package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 惠农网水果蔬菜条目表
 */
@Data
@Table(name = "eco_agriculture_items")
@Entity
public class EcoAgricultureItems implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 条目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", insertable = false, nullable = false)
    private Integer itemId;

    /**
     * 条目名字
     */
    @Column(name = "item_name", nullable = false)
    private String itemName;

    /**
     * 条目的级数，1级，2级，3级
     */
    @Column(name = "item_generation", nullable = false)
    private Integer itemGeneration;

    /**
     * 父条目的ID
     */
    @Column(name = "item_father_id")
    private Integer itemFatherId;

    
}