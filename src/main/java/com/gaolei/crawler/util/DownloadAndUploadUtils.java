package com.gaolei.crawler.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/12/16 9:56
 */
public class DownloadAndUploadUtils {
    public static String downloadFile(String urlStr) throws Exception {
        //读取fastdfs文件路径
        FastDFSClient fastDFSClient = new FastDFSClient("D:\\Coding\\JavaProgram\\gaolei-epwho-crawler\\src\\main\\resources\\fastdfs-client.properties");

        //获取文件名称
        String[] urlArray = urlStr.split("/");
        String lastName = urlArray[urlArray.length - 1];

//        //获取图片拓展名
//        String[] exnames = lastName.split("\\.");
//        String exname = exnames[exnames.length - 1];

        String pathname = "D:\\download" + "\\" + lastName;
        //要输出到的本地文件路径
        FileOutputStream out = new FileOutputStream(new File(pathname));

        //通过url获取连接对象
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();

        //获取输入流
        InputStream is = conn.getInputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = is.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        out.flush();
        out.close();
        is.close();
        String result = "http://49.4.84.15/" + fastDFSClient.uploadFile(pathname);
        return result;
    }
}
