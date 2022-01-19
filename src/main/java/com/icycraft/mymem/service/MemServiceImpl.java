package com.icycraft.mymem.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.mymem.config.RedisUtil;
import com.icycraft.mymem.dao.*;
import com.icycraft.mymem.entity.*;
import com.icycraft.mymem.util.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemServiceImpl implements MemService {


    @Autowired
    MemDao memDao;

    @Value("${rand.img.prefix}")
    String randImgPrefix;

    @Value("${img.prefix}")
    String imgPrefix;

    @Autowired
    LovedDao lovedDao;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    LovedService lovedService;

    @Autowired
    CommentService commentService;


    @Autowired
    MemoryRepository memoryRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    PictureRepository pictureDao;


    @Value("是,是的,不,如何,一个人,什么,没,有,能,怎么,你,我,个,人,以,已经,已")
    String filterItem;


    @Override
    public Memory getAMemory() {

        return memDao.getRandomMem();
    }

    @Override
    public Memory getMemoryByKey(int key) {

        String memStr = redisOperator.hget(RedisUtil.MEMS, key + "");

        return JSONObject.parseObject(memStr, Memory.class);
    }

    @Override
    public Memory getMemoryById(long id) {
        return memDao.selectById(id);
    }

    @Override
    public Memory addAMemory(Memory memory) {
        if (StringUtils.isEmpty(memory.getResource())) {

            PageRequest page = PageRequest.of(1, 1);

            String[] items = filterItem.split(",");
            String filterdContent = replaceBlank(memory.getContent());
            for (String item : items) {
                log.info("去除了"+item);
                filterdContent = filterdContent.replace(item, "");
                log.info("filterdContent:"+filterdContent);
            }
            List<Picture> list = pictureDao.findByContent(filterdContent, page).getContent();
            if (list.size()>0){
                memory.setResource(list.get(0).getUrl());
            }else {
                int i = new Random().nextInt(1000)+10000;
                memory.setResource(randImgPrefix +i+".jpg");
            }

        }else {
            memory.setResource(imgPrefix+memory.getResource());
        }
        if (StringUtils.isEmpty(memory.getLocation())){
            memory.setLocation("");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        memory.setCreateDate(sdf.format(new Date()));
        memDao.insert(memory);

        int depth = memory.getDepth();

        User user = userDao.selectById(memory.getUserId());

        if (depth<=user.getLimitDepth()){
            memoryRepository.save(memory);
        }

        return memory;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\\t|\\r|\\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }



    @Override
    public PageList<Memory> getMemoryByUserId(long userId, int page, int depth) {
        int start = page*5;

        QueryWrapper<Memory> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        qw.eq("del_flag",0);
        Integer count = memDao.selectCount(qw);

        PageList<Memory> pageList = new PageList<>();


        QueryWrapper<Memory> qwM = new QueryWrapper<>();
        qwM.eq("user_id",userId);
        qwM.eq("del_flag",0);
        if (depth!=10){
            qwM.eq("depth",depth);
        }
        qwM.last("ORDER BY create_date desc LIMIT " + start + ",5 ");
        List<Memory> memories = memDao.selectList(qwM);

        memories.forEach(memory -> memory.setLovedNum(lovedService.getMemLovedNum(memory.getId())));
        memories.forEach(memory -> memory.setCommentNum(commentService.getCommentNum(memory.getId())));

        pageList.setList(memories);
        pageList.setCount(count);

        return pageList;
    }

    @Override
    public PageList<Memory> getLovedByUserId(long userId, int page, int depth) {

        int start = page*5;

        QueryWrapper<Loved> qw = new QueryWrapper<>();
        qw.eq("user_id",userId);
        List<Long> memIds = lovedDao.selectList(qw).stream().map(Loved::getMemId).collect(Collectors.toList());

        PageList<Memory> pageList = new PageList<>();

        pageList.setCount(memIds.size());
        List<Memory> list = new ArrayList<>();
        if (memIds.size()>0){
             list = memDao.selectList(new LambdaQueryWrapper<Memory>().in(Memory::getId, memIds).eq(Memory::getDelFlag,0).last("ORDER BY create_date desc LIMIT " + start + ",5 "));
        }
        list.forEach(memory -> memory.setLovedNum(lovedService.getMemLovedNum(memory.getId())));
        list.forEach(memory -> memory.setCommentNum(commentService.getCommentNum(memory.getId())));

        pageList.setList(list);
        return pageList;
    }

    @Override
    public PageList<MemEtc> getMemoryRand() {

        List<MemEtc> memories = memDao.getMemEtcList();
        PageList<MemEtc> result = new PageList<>();
        result.setList(memories);
        result.setCount(500);
        return result;
    }

    @Override
    public int delMem(long id) {
        Memory memory = new Memory();
        memory.setDelFlag(1);
        QueryWrapper<Memory> qw = new QueryWrapper<>();
        qw.eq("id",id);

        return memDao.update(memory,qw);
    }

    @Override
    public List<Memory> saveAllToEs() {


        List<Memory> memories = memDao.getLimitMems();

        memoryRepository.saveAll(memories);

        return null;
    }

    @Override
    public PageList<Memory> searchMemory(int page, String content) {

        Pageable pageable = PageRequest.of(page,10);

        Page<Memory> byContent = memoryRepository.findByContent(content, pageable);

        List<SearchHit<Memory>> byContentWithHighLight = memoryRepository.findByContentWithHighLight(content, pageable);

        ArrayList<Memory> result = new ArrayList<>();
        for (SearchHit<Memory> memorySearchHit : byContentWithHighLight) {
            Memory memory = memorySearchHit.getContent();
            String contentHL = memorySearchHit.getHighlightField("content").get(0);
            memory.setContent(contentHL);
            int memLovedNum = lovedService.getMemLovedNum(memory.getId());
            memory.setLovedNum(memLovedNum);
            int commentNum = commentService.getCommentNum(memory.getId());
            memory.setCommentNum(commentNum);
            result.add(memory);
        }
        PageList<Memory> objectPageList = new PageList<>();
        objectPageList.setList(result);
        objectPageList.setCount(Integer.parseInt(byContent.getTotalElements()+""));


        return objectPageList;
    }

    @Override
    public String saveAllFromFileToMySql() {
        try {
            ArrayList<String> list = new ArrayList<>();
            String fileName = "C:/Users/ky/Desktop/20210318.txt";
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
            for (String tex : list) {

            }
        }catch (Exception e){

        }
            return null;
    }
}
