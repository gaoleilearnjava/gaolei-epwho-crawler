package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Table(name = "store_label_info")
@Entity
@Data
public class StoreLabel implements Serializable {
    private static final long serialVersionUID = 1L;
    //标签ID
    @Id
    @Column(name = "label_id")
    private String labelId;
    //标签名字
    @Column(name = "label_name")
    private String labelName;
    //标签状态1启用，9禁用
    @Column(name = "status")
    private Integer status;
    //标签排序
    @Column(name = "label_sort")
    private Integer labelSort;
    //标签创建时间
    @Column(name = "create_time")
    private String createTime;
    //标签创建用户
    @Column(name = "create_user")
    private String createUser;


}