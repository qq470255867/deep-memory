package com.icycraft.mymem.controller;

import com.icycraft.mymem.entity.Loved;
import com.icycraft.mymem.entity.WebResult;
import com.icycraft.mymem.service.LovedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loved")
@Slf4j
public class LovedController {


    @Autowired
    private LovedService lovedService;

    @PostMapping("/add")
    public WebResult addLoved(@RequestBody Loved loved){
        try {
            Loved result = lovedService.addLoved(loved);
            return WebResult.SUCCESS(result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


    @PostMapping("/del")
    public WebResult delLoved(@RequestBody Loved loved){
        try {
            Loved result = lovedService.delLoved(loved);
            return WebResult.SUCCESS(result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


    @PostMapping("/get")
    public WebResult getLoved(@RequestBody Loved loved){
        try {
            Loved result = lovedService.getLoved(loved);
            return WebResult.SUCCESS(result);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


}
