package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Comment {

    @TableId(type = IdType.AUTO)
    private int id;

    private String content;

    private int userId;

    private int memId;

    private String createDate;

    //关联查询用户的结果
    private String avatar;

    private String name;

    @TableField(exist = false)
    private int level;


    @TableField(exist = false)
    private String levelColor;

}
