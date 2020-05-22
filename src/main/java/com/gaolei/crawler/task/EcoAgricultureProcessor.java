package com.gaolei.crawler.task;

import com.gaolei.crawler.pojo.EcoAgricultureItems;
import com.gaolei.crawler.pojo.EcoAgricultureProducts;
import com.gaolei.crawler.service.EcoAgricultureService;
import com.gaolei.crawler.util.DownloadAndUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author 高磊
 * @version 1.0
 * @date 2020/5/11 13:45
 */
@Component
public class EcoAgricultureProcessor implements PageProcessor {
    @Autowired
    private EcoAgricultureService ecoAgricultureService;


    private Site site = Site.me()
            .setCharset("utf-8")//设置编码
            .setRetryTimes(0)//设置重试次数
            .setSleepTime(5)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");


    @Override
    public void process(Page page) {
        //如果是目录页
        if (page.getUrl().get().contains("zw")) {
            itemProcess(page);
        }
        //如果是列表页
        else if (page.getUrl().get().contains("/p/")) {
            listProcess(page);
        }
        //否则就是详情页
        else {
            productProcess(page);
        }
    }


    /**
     * 详情页的爬取逻辑
     *
     * @param page 爬取的页面
     */
    public void productProcess(Page page) {
        System.out.println("现在爬取的是： " + page.getUrl().get() + "  是详情页");
        //新建一个产品，并设置相应的参数
        EcoAgricultureProducts product = new EcoAgricultureProducts();
        //名称
        String productName = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[3]/div[1]/text()").get();
        product.setProductName(productName);
        //条目id
        //获取三级条目名称，并获取到相应的条目id
        String thirdItemName = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[1]/div/span[4]/a/text()").get();
        int thirdItemId = ecoAgricultureService.getItemIdByItemName(thirdItemName);
        product.setProductItem(thirdItemId);
        //价格
        String price1 = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[3]/div[2]/div/p/span/span[1]/text()").get();
        String price2 = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[3]/div[2]/div/p/span/span[2]/text()").get();
        String price = price1 + price2;
        product.setProductPrice(price);
        //发货地址
        String address = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[3]/div[3]/span[2]/text()").get();
        product.setProductAddress(address);
        //规格
        List<Selectable> productSpecs = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[3]/div[5]/ul/li").nodes();
        int end = Math.min(productSpecs.size(), 5);
        for (int i = 0; i < end; i++) {
            String spec = productSpecs.get(i).xpath("/li/div/div/div/p/text()").get();
            switch (i) {
                case 0:
                    product.setProductSpec1(spec);
                    break;
                case 1:
                    product.setProductSpec2(spec);
                    break;
                case 2:
                    product.setProductSpec3(spec);
                    break;
                case 3:
                    product.setProductSpec4(spec);
                    break;
                case 4:
                    product.setProductSpec5(spec);
                    break;
                default:
                    break;
            }
        }
        //图片
        List<Selectable> productPics = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div/div/div[3]/div[1]/div[2]/div[2]/ul/li").nodes();
        int picNum = Math.min(5, productPics.size());
        for (int i = 0; i < picNum; i++) {
            String picUrl = productPics.get(i).xpath("/li/span/img/@src").get().trim();
            String pic = null;
            try {
                pic = DownloadAndUploadUtils.downloadPicture(picUrl);
            } catch (Exception e) {
                System.out.println("下载并上传图片的过程中出了点bug，具体原因见下面");
                e.printStackTrace();
            }
            switch (i) {
                case 0:
                    product.setProductImg1(pic);
                    break;
                case 1:
                    product.setProductImg2(pic);
                    break;
                case 2:
                    product.setProductImg3(pic);
                    break;
                case 3:
                    product.setProductImg4(pic);
                    break;
                case 4:
                    product.setProductImg5(pic);
                    break;
                default:
                    break;
            }
        }
        //属性
        String attribute = page.getHtml().xpath("//*[@id=\"tab-title\"]/div[2]/div/div[1]/ul").get();
        product.setProductAttribute(attribute);
        //详情
        //文字详情
        String productWordDetail = page.getHtml().xpath("//*[@id=\"tab-title\"]/div[2]/div/div[2]").get();
        //视频详情
        String videoUrl = page.getHtml().xpath("//*[@id=\"tab-title\"]/div[2]/div/div[3]/a/video/source/@src").get();
        String productVideoUrl = null;
        try {
            productVideoUrl = DownloadAndUploadUtils.downloadPicture(videoUrl);
        } catch (Exception e) {
            System.out.println("视频在下载和上传的过程中出现了问题");
            e.printStackTrace();
        }
        product.setProductVideoDetail(productVideoUrl);
        //图片详情
        /*我们的图片地址，用逗号分隔*/
        String productPicDetail = "";
        List<Selectable> picDetails = page.getHtml().xpath("//*[@id=\"tab-title\"]/div[2]/div/div[3]/div/img").nodes();
        for (Selectable picDetail : picDetails) {
            String picUrl = picDetail.xpath("/img/@src").get();
            productPicDetail = productWordDetail + picUrl + ",";
        }
        product.setProductPicDetail(productPicDetail);
    }

    /**
     * 产品列表页的爬取逻辑
     *
     * @param page 爬取的目标页面
     */
    public void listProcess(Page page) {
        System.out.println("现在爬取的是： " + page.getUrl().get() + "  是列表页");

        //遍历产品列表，把详情URL加入到队列中。
        List<Selectable> productNodes = page.getHtml().xpath("//div[@class='product-contents']/div").nodes();
        for (Selectable productNode : productNodes) {
            String detailUrl = "https://www.cnhnb.com" + productNode.xpath("//a/@href").get();
            Request request = new Request(detailUrl);
            request.setPriority(0);
            page.addTargetRequest(request);
        }

        //获取到共多少页
        Selectable bottom = page.getHtml().xpath("//*[@id='__layout']/div/div/div[2]/div[4]");
        List<Selectable> pages = bottom.xpath("/div/div/a").nodes();
        int totalPageNum = Integer.parseInt(pages.get(pages.size() - 1).xpath("//text()").get());

        //当前的页数
        String url = page.getUrl().get();
        int currentPage = Integer.parseInt(bottom.xpath("//a[@class='number active']/text()").get());
        //把下一页加入到URL中
        if (currentPage < totalPageNum) {
            String nextPageUrl = "https://www.cnhnb.com" + pages.get(0).xpath("//@href").get().replace("1", "" + (currentPage + 1));
            Request request = new Request(nextPageUrl);
            request.setPriority(0);
            page.addTargetRequest(request);
        }
    }

    /**
     * 产品详情页的爬取逻辑
     *
     * @param page 爬取的目标页面
     */
    public void itemProcess(Page page) {
        System.out.println("现在爬取的是： " + page.getUrl().get() + "  是目录页");


        //先爬取水果的二级条目列表
        List<Selectable> secondItems = page.getHtml().xpath("//*[@id=\"__layout\"]/div/div/div[2]/div[1]/div/ul/li[@class='sub-row']").nodes();
        for (Selectable secondItem : secondItems) {
            //生成二级条目并加入到数据库中
            EcoAgricultureItems second = new EcoAgricultureItems();
            second.setItemFatherId(1);
            second.setItemGeneration(2);
            //获取到二级条目的名字
            String secondItemName = secondItem.xpath("//span[@class='second-cate-item']/text()").get();
            second.setItemName(secondItemName);
            ecoAgricultureService.addItem(second);

            //获取三级条目列表
            List<Selectable> thirdItems = secondItem.xpath("//a[@target='_blank']").nodes();
            for (Selectable thirdItem : thirdItems) {
                //生成三级条目并加入到数据库中
                EcoAgricultureItems third = new EcoAgricultureItems();
                third.setItemGeneration(3);
                //获取到对应的二级条目的id
                int secondItemId = ecoAgricultureService.getItemIdByItemName(secondItemName);
                third.setItemFatherId(secondItemId);
                //获取到三级条目的名字
                String thirdItemName = thirdItem.xpath("//text()").get();
                third.setItemName(thirdItemName);
                ecoAgricultureService.addItem(third);

                //获取到列表页的URL
                String url = thirdItem.xpath("//a/@href").get();
                url = "https://www.cnhnb.com" + url;
                //把列表页的URL加入到爬取队列中，并设置优先级较低
                Request request = new Request(url);
                request.setPriority(100);
                request.setUrl(url);
                page.addTargetRequest(request);
            }
        }
    }

    @Override
    public Site getSite() {
        return this.site;
    }
}
