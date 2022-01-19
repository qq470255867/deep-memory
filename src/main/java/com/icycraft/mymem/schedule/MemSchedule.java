package com.icycraft.mymem.schedule;

import com.alibaba.fastjson.JSONObject;
import com.icycraft.mymem.entity.MemEtc;
import com.icycraft.mymem.entity.PageList;
import com.icycraft.mymem.entity.Picture;
import com.icycraft.mymem.service.MemService;
import com.icycraft.mymem.service.PicService;
import com.icycraft.mymem.util.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

@Component
@Slf4j
public class MemSchedule {

//    同步随机mem到redis

    @Autowired
    MemService memService;

    @Autowired
    RedisOperator redisOperator;


    //5分钟执行一次
    @Scheduled(cron = "0 */5 * * * ?")
    @PostConstruct
    private void configureTasks() {
        StopWatch sw = new StopWatch();
        sw.start("获取随机mem到redis");
        PageList<MemEtc> memoryRand = memService.getMemoryRand();
        List<MemEtc> mems = memoryRand.getList();

        int i = 0 ;
        for (MemEtc mem : mems) {
            String memJson = JSONObject.toJSONString(mem);
            redisOperator.hset("mems",i+"",memJson);
            i++;

        }
        sw.stop();
        log.info(sw.prettyPrint());
    }




}
