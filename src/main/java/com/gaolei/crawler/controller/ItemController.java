package com.gaolei.crawler.controller;

import com.gaolei.crawler.pojo.PachongItem;
import com.gaolei.crawler.pojo.StoreItem;
import com.gaolei.crawler.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/13 18:07
 */
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/addSecondItem")
    public String addItems() {
        List<PachongItem> secondItems = itemService.findAllSecondItem();
        List<StoreItem> storeItems = new ArrayList<>();
        //根据二级条目生成商品条目
        for (PachongItem secondItem : secondItems) {
            StoreItem storeItem = new StoreItem();
            storeItem.setItemId(secondItem.getItemId());
            storeItem.setItemName(secondItem.getItemName());
            storeItem.setItemFullName(secondItem.getItemFullName());
            storeItem.setItemIcon("");
            storeItem.setItemIndex(1);
            storeItem.setItemFatherId(secondItem.getItemFatherId());
            storeItem.setItemCreateTime(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()));
            storeItem.setItemCreateUser("0X000");
            storeItem.setItemStatus(1);
            storeItems.add(storeItem);
        }
        itemService.addStoreItems(storeItems);
        return "添加二级目录成功！";
    }
}
