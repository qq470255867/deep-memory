package com.icycraft.mymem.controller;

import com.icycraft.mymem.config.LevelConfig;
import com.icycraft.mymem.entity.WebResult;
import com.icycraft.mymem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lv/")
@Slf4j
public class LvController {


    @Autowired
    UserService userService;


    @RequestMapping("/exp/add/{userId}/{exp}")
    public WebResult addExp(@PathVariable("userId") long userId,@PathVariable("exp") int exp) {

        try {
            return WebResult.SUCCESS(userService.addExp(userId,exp));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
    @RequestMapping("/exp/get/need/{level}")
    public WebResult getExpNeed(@PathVariable("level") int l){

       return WebResult.SUCCESS(LevelConfig.getUpExp(l));

    }

}
