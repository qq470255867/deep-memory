package com.icycraft.mymem.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "picture_index")
@Data
public class Picture {

    @Id
    private long id;

    private String url;

    private String content;


}
