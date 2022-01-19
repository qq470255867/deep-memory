package com.icycraft.mymem.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.mymem.dao.MemDao;
import com.icycraft.mymem.dao.PicDao;
import com.icycraft.mymem.dao.PictureRepository;
import com.icycraft.mymem.entity.MemEtc;
import com.icycraft.mymem.entity.Memory;
import com.icycraft.mymem.entity.PageList;
import com.icycraft.mymem.entity.Picture;
import com.icycraft.mymem.service.MemService;
import com.icycraft.mymem.service.PicService;
import com.icycraft.mymem.util.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* 请不要乱动这里的代码。

除非你知道自己在做什么。
Please DO NOT mess with the code here.

Unless you know what you're doing.
*/

@RestController
@Slf4j
public class SycController {

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    PicDao picDao;

    @Value("${rand.img.prefix}")
    String randImgPrefix;

    @Autowired
    MemDao memDao;

    @Value("${img.prefix}")
    String imgPrefix;

    @Value("是,是的,不,如何,一个人,什么,没,有,能,怎么,你,我,个,人,以,已经,已,听,说")
    String filterItem;

    @Autowired
    MemService memService;
    @Autowired
    RedisOperator redisOperator;

    @GetMapping("/es/delete/{id}")
    public String deleteId(@PathVariable("id") long id){
        pictureRepository.deleteById(id);
        return "ok";
    }

    @Autowired
    PicService picService;


    @GetMapping("/spider/pic")
    public String spiderPic(){
        try {

            int index = 13498;
            for (int i = 451; i < 3333; i++) {
//                Thread.sleep(100);
                String url = "https://www.hippopx.com/index.php?c=main&m=portal_loadmore&lang=zh&page=";
                System.out.println("------------第"+i+"页--------------");
                Connection connection = Jsoup.connect(url+i).timeout(300000).ignoreContentType(true);

                Document document = connection.get();
                Elements allElements = document.getElementsByTag("li");

                for (Element li : allElements) {
                    String picUrl = li.getElementsByTag("link").get(0).attr("href");
                    Element figcaption = li.getElementsByTag("figure").get(0).getElementsByTag("figcaption").get(0);
                    String content = figcaption.text();

//                    System.out.println(picUrl);
                    System.out.println(content);

                    downloadPicture(picUrl,"D:\\picture\\dm_"+index+".jpg");
                    Picture picture = new Picture();

                    String URL_SERVER = imgPrefix+"dm_"+index+",jpg";
                    picture.setId(index++);
                    picture.setContent(content);
                    picture.setUrl(URL_SERVER);

                    picDao.insert(picture);
                }

            }
            return "ok";
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return null;

        }

    }

    @GetMapping("/save/pic/toes")
    public void savePicToEs(){
        QueryWrapper<Picture> pictureQueryWrapper = new QueryWrapper<>();
//        pictureQueryWrapper.gt("id",100000);
        List<Picture> pictures = picDao.selectList(pictureQueryWrapper);

        pictureRepository.saveAll(pictures);
    }

    @GetMapping("/redis/syc")
    public String sycRedis(){
        PageList<MemEtc> memoryRand = memService.getMemoryRand();
        List<MemEtc> mems = memoryRand.getList();
        int i = 0 ;
        for (MemEtc mem : mems) {
            String json = JSONObject.toJSONString(mem);
            redisOperator.hset("mems",i+"",json);
            i++;
        }
        return "ok";
    }

    @GetMapping("/refresh/pic/{id}/{index}")
    public String refreshPic(@PathVariable("id") long id,@PathVariable("index") int index) {

        Memory memory = memService.getMemoryById(id);

        List<Picture> picturesSame = picService.getPicturesSame(memory.getContent());

        String url = picturesSame.get(index).getUrl();

        memory.setResource(url);

        memDao.updateById(memory);

        return memory.getResource();
    }




    private static void downloadPicture(String urlList,String path) {
        System.out.println("开始下载："+urlList);
        long start = System.currentTimeMillis();
        URL url = null;
        try {
            url = new URL(urlList);
            HttpURLConnection urlConnection=null;
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.93 Safari/537.36 Edg/96.0.1054.53"); //防止报403错误。
            InputStream is = urlConnection.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(is);

            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024*50];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            is.close();
            long m = System.currentTimeMillis()-start;
            m = m/1000;
            System.out.println("下载结束，耗时 "+m+"s");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String[] items = "是,是的,不,如何,一个人,什么,没,有,能,怎么,你,我,个,人,以,已经,已".split(",");
        String filterdContent = "我这个人什么都没有，没脑子，没钱，也没有未来，可是我喜欢一个人，我想要给她最好的结局。";
        for (String item : items) {
            filterdContent = filterdContent.replace(item, "");
        }
        System.out.println(filterdContent);

    }


    @GetMapping("/file/content/{name}")
    public String saveFileToDB(@PathVariable("name") String name){

        try {
            QueryWrapper<Memory> mqw = new QueryWrapper<>();
            mqw.eq("user_id","1");
            memDao.delete(mqw);
            ArrayList<String> list = new ArrayList<>();
            String fileName = "/home/dm/"+name+".txt";
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String text = "";
            while((line = br.readLine()) != null){
                if (line.contains("图片")) continue;
                if (line.contains("Chapter")) continue;
                if (line.contains("CHAPTER")) continue;
                if (line.contains("chapter")) continue;
                if (line.contains("♪")) continue;
                if ((line.contains("■■■"))) continue;
                // 一行一行地处理...
                Pattern p = Pattern.compile("^.*\\d+.*");
                Matcher m = p.matcher(line);
                if (!m.matches()) {
//            if (!line.contains(".")){
                    text+=line;
                }else if (!StringUtils.isEmpty(text)){
                    list.add(text);
                    text="";
                }
            }
            for (String content : list) {
                Memory memory = new Memory();
                Pageable pageable = PageRequest.of(1,1);
                String[] items = filterItem.split(",");
                String filterdContent = content;
                for (String item : items) {
                    log.info("去除了"+item);
                     filterdContent = filterdContent.replace(item, "");
                    log.info("filterdContent:"+filterdContent);
                }

                Page<Picture> byContent = pictureRepository.findByContent(filterdContent, pageable);


                String url = "";
                if (byContent.getContent().size()>0){
                    url = byContent.getContent().get(0).getUrl();

                }else {
                    int i = new Random().nextInt(1000)+10000;
                    url = randImgPrefix +i+".jpg";
                }
                memory.setContent(content);
                memory.setResource(url);
                memory.setUserId(1);
                memory.setResourceType(1);
                memory.setMemDate("2021-11-13");
                memory.setCreateDate("2021-11-14 15:18:54");
                memory.setLovedNum(0);
                memory.setDelFlag(0);
                memDao.insert(memory);
                log.info(memory.getContent());
            }


            return "OK";
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return "fail";
        }


    }






}
