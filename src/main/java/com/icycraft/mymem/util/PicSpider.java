package com.icycraft.mymem.util;

import com.icycraft.mymem.entity.Picture;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PicSpider {

    public static void main(String[] args) throws Exception{
        ArrayList<Picture> pictures = new ArrayList<>();

        for (int i = 1; i < 3333; i++) {
            String url = "https://www.hippopx.com/index.php?c=main&m=portal_loadmore&lang=zh&page=";
            System.out.println("------------第"+i+"页--------------");
            Connection connection = Jsoup.connect(url+i).timeout(300000).ignoreContentType(true);

            Document document = connection.get();
            Elements allElements = document.getElementsByTag("li");

            for (Element li : allElements) {
                String picUrl = li.getElementsByTag("link").get(0).attr("href");
                Element figcaption = li.getElementsByTag("figure").get(0).getElementsByTag("figcaption").get(0);
                String content = figcaption.text();

                System.out.println(picUrl);
                System.out.println(content);
            }

        }



    }
}
