package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "store_item_info")
@Data
public class StoreItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_full_name")
    private String itemFullName;

    @Column(name = "item_icon")
    private String itemIcon;

    @Column(name = "item_index")
    private Integer itemIndex;

    @Column(name = "item_father_id")
    private String itemFatherId;

    @Column(name = "item_create_time")
    private String itemCreateTime;

    @Column(name = "item_create_user")
    private String itemCreateUser;

    @Column(name = "item_last_update_time")
    private String itemLastUpdateTime;

    @Column(name = "item_last_update_user")
    private String itemLastUpdateUser;

    @Column(name = "item_status")
    private Integer itemStatus;

    @Column(name = "item_note")
    private String itemNote;


}