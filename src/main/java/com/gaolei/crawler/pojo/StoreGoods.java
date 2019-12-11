package com.gaolei.crawler.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "store_goods")
public class StoreGoods implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "goods_id", insertable = false, nullable = false)
  private String goodsId;

  @Column(name = "goods_name", nullable = false)
  private String goodsName;

  /**
   * 服务商ID
   */
  @Column(name = "sp_id", nullable = false)
  private String spId;

  @Column(name = "item_id")
  private String itemId;

  /**
   * 活动标志
   */
  @Column(name = "goods_activity")
  private Integer goodsActivity;

  /**
   * 上下架标志(0-下架 1-上架 9-删除)
   */
  @Column(name = "goods_up_or_low")
  private Integer goodsUpOrLow;

  @Column(name = "goods_up_or_low_date")
  private Date goodsUpOrLowDate;

  /**
   * 商品排序
   */
  @Column(name = "goods_sort")
  private Integer goodsSort;

  @Column(name = "goods_create_user", nullable = false)
  private String goodsCreateUser;

  @Column(name = "goods_create_time", nullable = false)
  private Date goodsCreateTime;

  @Column(name = "goods_last_update_user")
  private String goodsLastUpdateUser;

  @Column(name = "goods_last_update_time")
  private Date goodsLastUpdateTime;

  /**
   * 产品来源：0-本地;1-外部
   */
  @Column(name = "goods_source")
  private Integer goodsSource;

  
}