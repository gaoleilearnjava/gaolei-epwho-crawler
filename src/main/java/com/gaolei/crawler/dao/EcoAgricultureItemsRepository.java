package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.EcoAgricultureItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EcoAgricultureItemsRepository extends JpaRepository<EcoAgricultureItems, Integer>, JpaSpecificationExecutor<EcoAgricultureItems> {
    /**
     * 根据条目名字查询条目id
     *
     * @param itemName 条目名称
     * @return 条目id
     */
    @Query(value = "select item_id from eco_agriculture_items where item_name = ?1", nativeQuery = true)
    int getItemIdByItemName(@Param("itemName") String itemName);
}