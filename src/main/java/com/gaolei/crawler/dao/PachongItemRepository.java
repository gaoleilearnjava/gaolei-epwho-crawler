package com.gaolei.crawler.dao;

import com.gaolei.crawler.pojo.PachongItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PachongItemRepository extends JpaRepository<PachongItem, Void>, JpaSpecificationExecutor<PachongItem> {
    List<PachongItem> findAllByItemGeneration(Integer generation);
}