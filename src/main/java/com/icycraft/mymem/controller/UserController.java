package com.icycraft.mymem.controller;

import com.icycraft.mymem.entity.User;
import com.icycraft.mymem.entity.WebResult;
import com.icycraft.mymem.service.LovedService;
import com.icycraft.mymem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.Cache;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    LovedService lovedService;



    @RequestMapping("/get/{id}")
    public WebResult getUserById(@PathVariable("id")long  id) {
        try {
            User user = userService.getUserById(id);
            return WebResult.SUCCESS(user);
        }catch (Exception e) {
            return WebResult.ERROR(e.getMessage());
        }
    }

    @PostMapping("/update")
    public WebResult updateUserById(@RequestBody User user) {
        try {

            User result = userService.updateUser(user);

            return WebResult.SUCCESS(result);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @RequestMapping("/get/openId/{code}")
    public WebResult getUserOpenId(@PathVariable("code")String code){
        try {
            String openId =userService.getOpenId(code);
            return WebResult.SUCCESS(openId);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @RequestMapping("/login/{openId}")
    public WebResult login(@PathVariable("openId")String openId){
        try {
            User login = userService.login(openId);
            return WebResult.SUCCESS(login);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

}
