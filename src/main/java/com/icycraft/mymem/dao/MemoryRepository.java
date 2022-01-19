package com.icycraft.mymem.dao;

import com.icycraft.mymem.entity.Memory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Highlight;
import org.springframework.data.elasticsearch.annotations.HighlightField;
import org.springframework.data.elasticsearch.annotations.HighlightParameters;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRepository extends ElasticsearchRepository<Memory,Long> {

    @Query("{\"bool\" : {\"must\" : [ {\"match\" : {\"content\" : \"?0\"}}, {\"term\" : {\"delFlag\" : \"0\"}} ]}}")
    @Highlight(fields = {
            @HighlightField(name = "content")
    })
    Page<Memory> findByContent(String content, Pageable pageable);

    @Query("{\"bool\" : {\"must\" : [ {\"match\" : {\"content\" : \"?0\"}}, {\"term\" : {\"delFlag\" : \"0\"}} ]}}")
    @Highlight(
            parameters = @HighlightParameters(preTags = "<span style='color:red'>",postTags = "</span>"),
            fields = {
            @HighlightField(name = "content"),
    })
    List<SearchHit<Memory>> findByContentWithHighLight(String content, Pageable pageable);


}
