package com.gaolei.crawler.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "store_goods_details_info")
public class StoreGoodsDetailsInfo implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "goods_id", insertable = false, nullable = false)
  private String goodsId;

  /**
   * 商品详情
   */
  @Column(name = "goods_details", nullable = false)
  private String goodsDetails;

  /**
   * 解决方案
   */
  @Column(name = "goods_solution")
  private String goodsSolution;

  /**
   * 应用场景
   */
  @Column(name = "goods_scene")
  private String goodsScene;

  
}