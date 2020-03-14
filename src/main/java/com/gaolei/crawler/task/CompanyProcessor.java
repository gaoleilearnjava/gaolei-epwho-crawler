package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.CompanyPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/3/11 18:24
 */
@Component
public class CompanyProcessor implements PageProcessor {

    @Autowired
    private CompanyPipeline companyPipeline;

    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(5)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");

    @Override
    public void process(Page page) {
        String url = page.getUrl().get();
        System.out.println(url);
        if (url.contains("search")) {

            //列表页应该爬取详情页
            List<Selectable> nodes = page.getHtml().xpath("//div[@class='search-name']/a/@href").nodes();
            String detail = "";
            if (nodes.size() > 0) {
                detail = nodes.get(0).get();
            }
            if (!detail.equals("")) {
                page.addTargetRequest(detail);
            }
        } else if (page.getUrl().get().contains("company")) {
            //获取名字
            String name = page.getHtml().xpath("//div[@class='inner-name inner-title']/text()").get();
            //获取详情信息
            Selectable info = page.getHtml().xpath("//div[@class='detail-container']").nodes().get(0);
            //获取所属行业
            String item = info.xpath("//div[2]/div[@class='content']/div[3]/div[2]/div[2]/text()").get();
            //获取经营范围
            String field = info.xpath("//div[2]/div[@class='content']/div[5]/div[4]/div[2]/div/div/text()").get();

            //打印信息
            System.out.println("************************************");
            System.out.println("企业名称        " + name);
            System.out.println("所属行业        " + item);
            System.out.println("经营范围        " + field);
            System.out.println("************************************");
            page.putField("name", name);
            page.putField("item", item);
            page.putField("field", field);
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }

//    /**
//     * 正式任务执行方法
//     */
//    //定时任务,开始爬虫,每小时执行一次
//    @Scheduled(cron = "0 0 * * * ?")
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
//    public void process() throws IOException {
//
//        Spider spider = Spider.create(new CompanyProcessor())
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
//                .addPipeline(companyPipeline)//添加pipeline
//                .thread(1);//设置多线程
//        File file = new File("C:\\Users\\17631\\Desktop\\pa_list.csv");
//        FileReader fileReader = new FileReader(file);
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        String qichacha = "";
//        int count = 0;
//        while ((qichacha = bufferedReader.readLine()) != null) {
//            count++;
//            String tianyancha = qichacha.replaceAll("qichacha", "tianyancha");
//            System.out.println(count + "    " + tianyancha);
//            spider.addUrl(tianyancha);
//        }
//        spider.run();
//        bufferedReader.close();
//    }
}
