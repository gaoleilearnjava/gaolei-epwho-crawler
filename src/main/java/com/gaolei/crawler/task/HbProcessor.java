package com.gaolei.crawler.task;

import com.gaolei.crawler.pipeline.HbProductPipeline;
import com.gaolei.crawler.pojo.HbProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 高磊
 * @version 1.0
 * @date 2019/11/13 11:35
 */
@Component
public class HbProcessor implements PageProcessor {
    @Autowired
    private HbProductPipeline hbProductPipeline;

    Logger logger = LoggerFactory.getLogger(EpwhoProcessor.class);
    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setTimeOut(30 * 1000)//设置超时时间
            .setRetrySleepTime(30 * 1000)//设置重试的时间间隔
            .setRetryTimes(3)//设置重试次数
            .setSleepTime(0);   //设置睡眠时间0


    @Override
    public void process(Page page) {
        //获取产品种类的大列表
        List<Selectable> nodes = page.getHtml().xpath("//body/div[@class='evli_pro']/div[@class='evli_pro_list1']").nodes();
        if (nodes.size() != 0) {
            //说明是种类列表页面
            for (Selectable node : nodes) {
                List<Selectable> types = node.xpath("//dl/dt").nodes();
                for (Selectable type : types) {
                    //获取该分类的商品列表网址
                    String productListHtml = type.xpath("//a/@href").toString();
                    //将分类的商品列表网址加入到爬取队列中
                    page.addTargetRequest(productListHtml);
                }
            }
        } else {
            //说明是产品列表页面或者是产品详情页面
            //判断是列表页还是产品详情页
            if (page.getUrl().toString().contains("chanpin")) {
                //爬取产品列表页
                processProductList(page);
            } else {
                //爬取商品详情页
                if (page.getHtml().xpath("//*[@class='location']//a[1]/text()").toString() != null) {
                    //第一种(常见类型)
                    processProductDtlType1(page);
                } else {
                    //第二种(不常见类型)
                    processProductDtlType2(page);
                }
            }
        }
    }

    /**
     * 爬取产品列表页
     *
     * @param page
     */
    private void processProductList(Page page) {
        //产品主列表页
        Selectable productsNode = page.getHtml().xpath("//body//div[@class='wrapper']/div[@class='proBox1']");
        if (productsNode != null) {
            //获取所有的产品列表
            List<Selectable> productDtlLists = productsNode.xpath("//ul/li").nodes();
            for (Selectable productDtl : productDtlLists) {
                //不是标王就加入爬取列表
                String type = productDtl.xpath("/li/@class").toString();
                if (type.equals("")) {
                    //获取到产品详情链接
                    String productDtlUrl = productDtl.xpath("//h3/a/@href").toString();
                    //将产品详情链接加入到爬取队列
                    page.addTargetRequest(productDtlUrl);
                }
            }
        }
        //视频产品列表页
        Selectable videoProductsNode = page.getHtml().xpath("//body/div[@class='wrapper']/div[@class='proBoxRight']");
        if (videoProductsNode != null) {
            List<Selectable> videoProductDtlLists = videoProductsNode.xpath("//div[@class='proBoxRight']/ul/li").nodes();
            for (Selectable videoProductDtl : videoProductDtlLists) {
                String videoProductDtlUrl = videoProductDtl.xpath("//h2/a/@href").toString();
                //将视频产品详情链接加入到爬取队列
                page.addTargetRequest(videoProductDtlUrl);
            }
        }

        //先获取共多少页
        String pageString = page.getHtml().xpath("//span[@class='jump']/text()").nodes().get(0).toString();
        int indexOfPage = pageString.indexOf("页");
        String pageStr = pageString.substring(1, indexOfPage);
        Integer pageNum = Integer.valueOf(pageStr);
        //获取当前是多少页
        String url = page.getUrl().toString();
        //将下一页加入到爬取列表中
        if (url.contains("_")) {
            String[] strs = url.split("_");
            String head = strs[0];
            String tail = strs[1];
            Integer nextPage = Integer.valueOf(tail.split("\\.")[0].substring(1)) + 1;
            System.out.println("下一页是" + nextPage);
            if (nextPage <= pageNum) {
                String nextPageUrl = head + "_p" + nextPage + ".html";
                System.out.println(nextPageUrl);
                page.addTargetRequest(nextPageUrl);
            }
        } else {
            if (pageNum >= 3) {
                String[] strings = url.split("\\.");
                String nextPageUrl = strings[0] + "." + strings[1] + "." + strings[2] + "_p2.html";
                System.out.println(nextPageUrl);
                page.addTargetRequest(nextPageUrl);
            }
        }
    }


    /**
     * 第一种详情页面
     *
     * @param page
     */
    private void processProductDtlType1(Page page) {
        //产品详情页
        Html html = page.getHtml();
        HbProduct product = new HbProduct();
        //获取创建时间
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        product.setCreate_time(dateFormat.format(date));

        //获取生产厂家
        String companyName = html.xpath("//*[@class='location']//a[1]/text()").toString();
        if (companyName != null)
            product.setProduct_company(companyName);
        else {
            //没获取到厂家名就跳过本页
            page.setSkip(true);
        }

        //获取产品类别
        String productType = html.xpath("//*[@class='location']//a[3]/text()").toString();
        if (productType == null)
            productType = html.xpath("//*[@class='location']//a[2]/text()").toString();
        if (productType != null)
            product.setProduct_type(productType);

        //获取产品名字
        String productName = html.xpath("//*[@class='productName']//h1//text()").toString();
        if (productName != null) {
            product.setProduct_name(productName);
            //根据产品名字生成ID
            String productId = UUID.nameUUIDFromBytes((productName).getBytes()).toString().replaceAll("-", "");
            product.setProduct_id(productId);
        } else
            page.setSkip(true);

        //获取产品的五张图片
        List<Selectable> photoNodes = html.xpath("//body//div[@class='productPhoto']/div[@class='smallImg']/ul/li").nodes();
        if (photoNodes.size() >= 1)
            product.setProduct_pic1(photoNodes.get(0).xpath("//img/@src").toString());
        if (photoNodes.size() >= 2)
            product.setProduct_pic2(photoNodes.get(1).xpath("//img/@src").toString());
        if (photoNodes.size() >= 3)
            product.setProduct_pic3(photoNodes.get(2).xpath("//img/@src").toString());
        if (photoNodes.size() >= 4)
            product.setProduct_pic4(photoNodes.get(3).xpath("//img/@src").toString());
        if (photoNodes.size() >= 5)
            product.setProduct_pic5(photoNodes.get(4).xpath("//img/@src").toString());

        //获取产品数据模块.
        Selectable dataNode = html.xpath("//div[@class='productRt']");
        if (dataNode.toString() != null) {
            //获取产品价格
            String price = dataNode.xpath("//div[@class='price']//td[@class='sale']/text()").toString();
            if (price != null)
                product.setProduct_price(price);

            //获取产品最小订货量
            String minOrder = dataNode.xpath("//div[@class='price']//span[@class='jisuanProNum']/text()").toString();
            if (minOrder != null)
                product.setProduct_min_ordered(minOrder + "台");

            List<Selectable> dataNods = dataNode.xpath("//div[@class='proDetail0']/ul/li").nodes();
            for (Selectable dataNod : dataNods) {
                String key = dataNod.xpath("//span/text()").toString();
                String value = dataNod.xpath("//b/text()").toString();
                if (key != null && value != null)
                    switch (key) {
                        //获取产品型号
                        case "型号":
                            product.setProduct_model(value);
                            break;
                        //获取产品品牌
                        case "品牌":
                            product.setProduct_brand(value);
                            break;
                        //获取厂商性质
                        case "厂商性质":
                            product.setCompany_type(value);
                            break;
                        //获取所在地
                        case "所在地":
                            product.setProduct_location(value);
                            break;
                    }
            }
            // 获取更新时间
            String updateTime = dataNode.xpath("//div[@class='productBtn']/p[1]/b[1]/text()").toString();
            product.setUpdate_time(updateTime);
        } else {
            //获取产品价格
            String price = html.xpath("//div[@class='price']//strong/text()").toString();
            if (price != null)
                product.setProduct_price(price);
            String parameters = html.xpath("//div[@class='parameter']/text()").toString();
            if (parameters != null) {
                String[] parameterList = parameters.split("\\s+");
                if (parameterList.length == 7) {
                    //获取产品型号
                    String productModel = parameterList[1];
                    product.setProduct_model(productModel);
                    //获取厂商性质
                    String companyType = parameterList[2];
                    product.setCompany_type(companyType);
                    //获取所在地
                    String location = parameterList[3];
                    product.setProduct_location(location);
                    //获取更新时间
                    String updateTime = parameterList[4] +" "+ parameterList[5];
                    product.setUpdate_time(updateTime);
                } else if (parameterList.length == 6){
                    //获取厂商性质
                    String companyType = parameterList[1];
                    product.setCompany_type(companyType);
                    //获取所在地
                    String location = parameterList[2];
                    product.setProduct_location(location);
                    //获取更新时间
                    String updateTime = parameterList[3] +" "+ parameterList[4];
                    product.setUpdate_time(updateTime);
                }
            }
        }


        //获取产品简介
        String profile = html.xpath("//body/div[@class='main']/article[1]//div[@class='aboutUsText']").toString();
        Selectable tempNode = html.xpath("//div[@class='main']//div[@class='text']");
        if (profile == null) {
            if (tempNode.nodes().size() != 0)
                profile = tempNode.nodes().get(0).toString();
            else
                profile = page.getHtml().xpath("//div[@class='simple']").toString();
        }
        if (profile != null)
            product.setProduct_profile(profile);

        //获取产品详情
        String detail = html.xpath("//body/div[@class='main']/article[2]//div[@class='aboutUsText']").toString();
        if (detail == null) {
            if (tempNode.nodes().size() != 0)
                detail = tempNode.nodes().get(1).toString();
            else
                detail = page.getHtml().xpath("//div[@class='pdetaile']").toString();
        }
        if (detail != null)
            product.setProduct_detail(detail);

        //获取产品详情页地址
        String url = page.getUrl().toString();
        product.setProduct_dtl_url(url);

        //获取产品来源
        product.setProduct_source(3);

        //将商品存入到pipeline中,由pipeline存入到数据库当中
        page.putField("product", product);
    }


    /**
     * 第二种详情页面
     *
     * @param page
     */
    private void processProductDtlType2(Page page) {
        //todo 写一下具体逻辑
    }


    @Override
    public Site getSite() {
        return this.site;
    }


    /**
     * 正式任务执行方法
     */
    //定时任务,开始爬虫,每小时执行一次
    @Scheduled(cron = "0 0 * * * ?")
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
    public void process() {
        logger.info("环保在线-商城产品列表任务开始执行...");
        String url = "http://www.hbzhan.com/product/newtype.html";
        Spider.create(new HbProcessor())
                .addUrl(url)//添加网址
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
                .addPipeline(hbProductPipeline)//添加pipeline
                .thread(5)//设置多线程
                .run();//启动
    }

//    /**
//     * 测试方法
//     */
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 1000)
//    public void process() {
//        logger.info("环保在线-商城产品列表任务开始执行...");
//        String url = "http://www.hbzhan.com/chanpin-9077.html";
//        Spider.create(new HbProcessor())
//                .addUrl(url)//添加网址
//                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10 * 10000)))//添加布隆过滤器)//添加任务队列
//                .addPipeline(hbProductPipeline)//添加pipeline
//                .thread(1)//设置多线程
//                .run();//启动
//    }

}
