package com.icycraft.mymem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "memory_index")
public class Memory {

    @TableId(type = IdType.AUTO)
    @Id
    private long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String content;

    private String resource;

    //1 pic 2 video 3music
    private int resourceType;

    private int depth;

    private long userId;

    private String memDate;

    private String createDate;

    private String location;

    private int lovedNum;

    private int commentNum;

    private int delFlag;
}
