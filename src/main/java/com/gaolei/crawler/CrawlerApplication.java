package com.gaolei.crawler;

import com.gaolei.crawler.pojo.Permit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 14:18
 */
@SpringBootApplication
@EnableScheduling
public class CrawlerApplication {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver",
                "C://chromedriver.exe");
        // 第二步：初始化驱动
        WebDriver driver = new ChromeDriver();
        // 第三步：获取目标网页
        driver.get("http://permit.mee.gov.cn/permitExt/syssb/xkgg/xkgg!licenseInformation.action");
        int count = 0;
        File file = new File("C:\\Users\\17631\\Desktop\\permit.csv");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        for (int i = 2; i <= 16055; i++) {
            List<WebElement> details = driver.findElements(By.xpath("//table[@cellspacing='0']/tbody/tr"));
            details.remove(0);
            for (WebElement detail : details) {
                List<WebElement> elements = detail.findElements(By.tagName("td"));

                Permit permit = new Permit();
                permit.setProvince(elements.get(0).getAttribute("title"));
                permit.setLocation(elements.get(1).getAttribute("title"));
                permit.setPermitNum(elements.get(2).getAttribute("title"));
                permit.setCompanyName(elements.get(3).getAttribute("title"));
                permit.setCompanyType(elements.get(4).getAttribute("title"));
                permit.setValidDate(elements.get(5).getAttribute("title"));
                permit.setPermitDate(elements.get(6).getAttribute("title"));
                permit.setCompanyUrl(elements.get(7).findElement(By.tagName("a")).getAttribute("href"));
                count++;
                try {
                    bufferedWriter.write(count + " " + permit.getProvince() + " " + permit.getLocation() + " " + permit.getPermitNum() + " " + permit.getCompanyName()
                            + " " + permit.getCompanyType() + " " + permit.getValidDate() + " " + permit.getPermitDate() + " " + permit.getCompanyUrl());
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    System.out.println("写入文件出错");
                    e.printStackTrace();
                }
            }
            // 第四步：解析。以下就可以进行解了。使用webMagic、jsoup等进行必要的解析
            WebElement nextPage = driver.findElement(By.xpath("//div[@class='fr margin-t-33 margin-b-20']/a[@onclick='javascript:jumpPage2(" + i + ")']"));
            nextPage.click();
        }
        bufferedWriter.close();
        driver.close();
    }
}
