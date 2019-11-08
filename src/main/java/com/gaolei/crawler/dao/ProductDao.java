package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 15:37
 */
public interface ProductDao extends JpaRepository<Product, String> {

    //根据商品详情页地址确定商品并更新商品
    @Modifying
    @Transactional
    @Query("update Product sep set " +
            "sep.product_name = CASE WHEN :#{#product.product_name} IS NULL THEN sep.product_name ELSE :#{#product.product_name} END ," +
            "sep.product_pic = CASE WHEN :#{#product.product_pic} IS NULL THEN sep.product_pic ELSE :#{#product.product_pic} END ," +
            "sep.product_type = CASE WHEN :#{#product.product_type} IS NULL THEN sep.product_type ELSE :#{#product.product_type} END ," +
            "sep.product_profile =  CASE WHEN :#{#product.product_profile} IS NULL THEN sep.product_profile ELSE :#{#product.product_profile} END, " +
            "sep.product_status =  CASE WHEN :#{#product.product_status} IS NULL THEN sep.product_status ELSE :#{#product.product_status} END ," +
            "sep.product_dtl_url =  CASE WHEN :#{#product.product_dtl_url} IS NULL THEN sep.product_dtl_url ELSE :#{#product.product_dtl_url} END ," +
            "sep.product_dtl_status =  CASE WHEN :#{#product.product_dtl_status} IS NULL THEN sep.product_dtl_status ELSE :#{#product.product_dtl_status} END ," +
            "sep.product_brand =  CASE WHEN :#{#product.product_brand} IS NULL THEN sep.product_brand ELSE :#{#product.product_brand} END ," +
            "sep.product_detail =  CASE WHEN :#{#product.product_detail} IS NULL THEN sep.product_detail ELSE :#{#product.product_detail} END ," +
            "sep.product_price =  CASE WHEN :#{#product.product_price} IS NULL THEN sep.product_price ELSE :#{#product.product_price} END ," +
            "sep.product_min_ordered =  CASE WHEN :#{#product.product_min_ordered} IS NULL THEN sep.product_min_ordered ELSE :#{#product.product_min_ordered} END ," +
            "sep.product_supply =  CASE WHEN :#{#product.product_supply} IS NULL THEN sep.product_supply ELSE :#{#product.product_supply} END ," +
            "sep.product_send_term =  CASE WHEN :#{#product.product_send_term} IS NULL THEN sep.product_send_term ELSE :#{#product.product_send_term} END ," +
            "sep.product_valid_term =  CASE WHEN :#{#product.product_valid_term} IS NULL THEN sep.product_valid_term ELSE :#{#product.product_valid_term} END ," +
            "sep.update_time =  CASE WHEN :#{#product.update_time} IS NULL THEN sep.update_time ELSE :#{#product.update_time} END ," +
            "sep.product_source =  CASE WHEN :#{#product.product_source} IS NULL THEN sep.product_source ELSE :#{#product.product_source} END ," +
            "sep.product_item =  CASE WHEN :#{#product.product_item} IS NULL THEN sep.product_item ELSE :#{#product.product_item} END ," +
            "sep.company_id =  CASE WHEN :#{#product.company_id} IS NULL THEN sep.company_id ELSE :#{#product.company_id} END " +
            "where sep.product_dtl_url = :#{#product.product_dtl_url}")
    void updateById(@Param("product") Product product);
}
