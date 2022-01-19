package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;


@Data
public class OpLog {

    @TableId(type = IdType.AUTO)
    private long id;

    private int userId;

    private String op;

    private String content;

    private Date date;


}
