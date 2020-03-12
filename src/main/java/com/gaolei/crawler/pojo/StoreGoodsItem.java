package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "store_goods_item_info")
@Entity
@Data
public class StoreGoodsItem implements Serializable {
    private static final long serialVersionUID = 1L;

    //条目ID
    @Id
    @Column(name = "item_id", insertable = false, nullable = false)
    private String itemId;

    //条目名字
    @Column(name = "item_name", nullable = false)
    private String itemName;

    //条目全名（从父条目名字开始）
    @Column(name = "item_full_name", nullable = false)
    private String itemFullName;

    //条目商标
    @Column(name = "item_icon")
    private String itemIcon;

    //条目索引
    @Column(name = "item_index")
    private Integer itemIndex;

    //父条目ID
    @Column(name = "item_father_id")
    private String itemFatherId;

    //条目创建时间
    @Column(name = "item_create_time", nullable = false)
    private String itemCreateTime;

    //条目创建用户
    @Column(name = "item_create_user", nullable = false)
    private String itemCreateUser;

    //条目更新时间
    @Column(name = "item_last_update_time")
    private String itemLastUpdateTime;

    //条目更新用户
    @Column(name = "item_last_update_user")
    private String itemLastUpdateUser;

    //条目状态，1是启用，9是禁用
    @Column(name = "item_status", nullable = false)
    private Integer itemStatus;

    /**
     * 类目描述
     */
    @Column(name = "item_note")
    private String itemNote;

    
}