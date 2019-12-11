package com.gaolei.sql;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/12/11 9:17
 */
public class DoSomething {
    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void doSomething() {

    }
}
