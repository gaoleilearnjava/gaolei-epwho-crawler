package com.gaolei.crawler.pipeline;

import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 18:20
 */
@Component
public class CompanyPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        String name = resultItems.get("name");
        String item = resultItems.get("item");
        String field = resultItems.get("field");
        File file = new File("C:\\Users\\17631\\Desktop\\result.csv");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(name + "  " + item + "  " + field);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("写入文件出错");
            e.printStackTrace();
        }
    }
}
