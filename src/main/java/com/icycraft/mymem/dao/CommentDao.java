package com.icycraft.mymem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.mymem.entity.Comment;
import com.icycraft.mymem.entity.Loved;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends BaseMapper<Comment> {


    @Select("SELECT\n" +
            "\ta.*,\n" +
            "\tb.NAME,\n" +
            "\tb.avatar, \n" +
            "\tb.level \n" +
            "FROM\n" +
            "\t`comment` a\n" +
            "\tLEFT JOIN `user` b \n" +
            "\ton a.user_id = b.id\n" +
            "\twhere a.mem_id = #{memId}\n" +
            "ORDER BY\n" +
            "\ta.create_date desc\n" +
            "limit #{start},5")
    List<Comment> getComments(long memId,int start);


}
