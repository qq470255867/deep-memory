package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private long id;

    private String name;

    private String sex;

    private String avatar;

    private String introduce;

    private int lovedNum;

    private String wxId;

    private int limitDepth;

    private int curExp;

    private int level;


}
