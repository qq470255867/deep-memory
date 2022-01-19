package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Loved {
    @TableId(type = IdType.AUTO)
    private long  id;

    private long userId;

    private long memId;

    @TableField(exist = false)
    private String memUserId;

}
