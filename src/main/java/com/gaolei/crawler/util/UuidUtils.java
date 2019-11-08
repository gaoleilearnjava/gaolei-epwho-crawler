package com.gaolei.crawler.util;

import java.util.UUID;
//已经看完
/**
 * UUID工具类
 *
 * @Author L.F
 * @Date 2018.4.17
 */
public class UuidUtils {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
