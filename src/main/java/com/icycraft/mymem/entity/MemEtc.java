package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class MemEtc {


    private long id;

    private String content;

    private String resource;

    //1 pic 2 video 3music
    private int resourceType;

    private int depth;

    private String memDate;

    private String createDate;

    private String location;

    private int lovedNum;

    private int commentNum;

    private String userId;

    private String name;

    private String sex;

    private String avatar;

    private String introduce;

    private int userLovedNum;

    private int level;

}
