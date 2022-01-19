package com.icycraft.mymem.controller;

import com.alibaba.fastjson.JSONObject;
import com.icycraft.mymem.dao.MemDao;
import com.icycraft.mymem.entity.*;
import com.icycraft.mymem.service.CommentService;
import com.icycraft.mymem.service.LovedService;
import com.icycraft.mymem.service.MemService;
import com.icycraft.mymem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/mem")
@Slf4j
public class MemController {

    @Autowired
    MemService memService;

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Autowired
    LovedService lovedService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @GetMapping("/get")
    public WebResult getAMemory(){

        try {

            Memory aMemory = memService.getAMemory();
            int memLovedNum = lovedService.getMemLovedNum(aMemory.getId());
            aMemory.setLovedNum(memLovedNum);
            return WebResult.SUCCESS(aMemory);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/get/rand/{key}")
    public WebResult getARandMemory(@PathVariable("key") int key){

        JSONObject result = new JSONObject();

        try {
            Memory mem = memService.getMemoryByKey(key);

            result.put("mem",mem);

            int memLovedNum = lovedService.getMemLovedNum(mem.getId());

            int userLovedNum = lovedService.getUserLovedNum(mem.getUserId());

            int commentNum = commentService.getCommentNum(mem.getId());

            User userById = userService.getUserById(mem.getUserId());

            result.put("user",userById);

            result.put("memLovedNum",memLovedNum);

            result.put("userLovedNum",userLovedNum);

            result.put("commentNum",commentNum);

            return WebResult.SUCCESS(result);

        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public WebResult getMemory(@PathVariable("id") int id){

        JSONObject result = new JSONObject();

        try {
            Memory mem = memService.getMemoryById(id);

            result.put("mem",mem);

            int memLovedNum = lovedService.getMemLovedNum(mem.getId());

            int userLovedNum = lovedService.getUserLovedNum(mem.getUserId());

            int commentNum = commentService.getCommentNum(mem.getId());

            User userById = userService.getUserById(mem.getUserId());

            result.put("user",userById);

            result.put("memLovedNum",memLovedNum);

            result.put("userLovedNum",userLovedNum);

            result.put("commentNum",commentNum);

            return WebResult.SUCCESS(result);

        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }




    @GetMapping("/get/list")
    public WebResult getList() {
        try {
            PageList<MemEtc> memoryRand = memService.getMemoryRand();

            return WebResult.SUCCESS(memoryRand);
        }catch (Exception e){
            return WebResult.ERROR(e.getMessage());
        }
    }



    @GetMapping("/getByUserId/{userId}/{page}/{depth}")
    public WebResult getByUserId(@PathVariable("userId") long userId,@PathVariable("page")int page,@PathVariable("depth") int depth){

        try {

            return WebResult.SUCCESS(memService.getMemoryByUserId(userId,page,depth));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }

    @GetMapping("/loved/getByUserId/{userId}/{page}/{depth}")
    public WebResult getLovedByUserId(@PathVariable("userId") long userId,@PathVariable("page")int page,@PathVariable("depth") int depth){

        try {

            return WebResult.SUCCESS(memService.getLovedByUserId(userId,page,depth));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }


    @GetMapping("/idGet/{id}")
    public WebResult getMemoryById(@PathVariable("id") long id){

        JSONObject result = new JSONObject();
        try {
            Memory mem = memService.getMemoryById(id);

            result.put("mem",mem);

            int memLovedNum = lovedService.getMemLovedNum(mem.getId());

            int userLovedNum = lovedService.getUserLovedNum(mem.getUserId());

            int commentNum = commentService.getCommentNum(mem.getId());

            User userById = userService.getUserById(mem.getUserId());

            result.put("user",userById);

            result.put("memLovedNum",memLovedNum);

            result.put("userLovedNum",userLovedNum);

            result.put("commentNum",commentNum);

            return WebResult.SUCCESS(result);

        }catch (Exception e){
            return WebResult.ERROR(e.getMessage());
        }


    }



    @PostMapping("/post")
    public WebResult postAMemory(@RequestBody Memory memory) {
        try {
            Memory aMemory = memService.addAMemory(memory);
            return WebResult.SUCCESS(aMemory);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


    @PostMapping("/upload/file")
    public WebResult uploadFile(MultipartFile file){
        try {
            String fileName = voidSameFileName(file.getOriginalFilename());
            String filePath = fileUploadPath +fileName;
            File dest = new File(filePath);
            file.transferTo(dest);

            return WebResult.SUCCESS(fileName);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }
    @GetMapping("/del/{id}")
    public WebResult delMem(@PathVariable("id") long memId){
        try {

            memService.delMem(memId);
            return WebResult.SUCCESS(memId);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/es/saveAll")
    public WebResult saveAll(){
        try {
            return WebResult.SUCCESS(memService.saveAllToEs());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }

    @GetMapping("/file/saveAll")
    public WebResult saveFileAll(){
        try {
            return WebResult.SUCCESS(memService.saveAllFromFileToMySql());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }


    @PostMapping("/search/mem")
    public WebResult searchMem(@RequestBody JSONObject jsonObject){
        String content = jsonObject.getString("content");
        int page = jsonObject.getInteger("page");

        try {
            return WebResult.SUCCESS(memService.searchMemory(page,content));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }


    }

    String voidSameFileName(String name){
        String[] split = name.split("\\.");
        if (split.length==1){
            return name;
        }else {
            String prefix = name.replaceAll("\\." + split[split.length - 1], "");
            return prefix+System.currentTimeMillis()+"."+split[split.length - 1];
        }
    }



}
