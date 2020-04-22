package com.gaolei.crawler.ocr;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/27 9:39
 */
public class Son extends Father {
    public Son(String name) {
        this.setName(name);
    }

    public String say() {
        return this.getName();
    }

    public static void main(String[] args) {
        System.out.println(new Son("baba").say());
    }
}
