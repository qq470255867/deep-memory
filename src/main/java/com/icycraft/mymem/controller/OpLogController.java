package com.icycraft.mymem.controller;

import com.icycraft.mymem.entity.OpLog;
import com.icycraft.mymem.entity.WebResult;
import com.icycraft.mymem.service.OpLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OpLogController {


    @Autowired
    OpLogService opLogService;



    @PostMapping("/log/add")
    public WebResult addLog(@RequestBody OpLog opLog){
        try {
            return WebResult.SUCCESS(opLogService.addLog(opLog));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }


    }


}
