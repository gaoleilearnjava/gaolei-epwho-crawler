package com.gaolei.crawler.ocr;


import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 获取token类
 */
public class AuthService {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\17631\\Desktop\\下载.jpg");
        FileInputStream fis = new FileInputStream(file);
        byte[] buff = new byte[(int) file.length()];
        int offset = 0;
        int numRead = 0;
        while (offset < buff.length
                && (numRead = fis.read(buff, offset, buff.length - offset)) >= 0) {
            offset += numRead;
        }
        String encode = Base64Utils.encodeToString(buff);
        String encode1 = URLEncoder.encode(encode, "utf-8");
        System.out.println(encode);

    }
}