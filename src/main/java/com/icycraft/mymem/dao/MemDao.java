package com.icycraft.mymem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.mymem.entity.MemEtc;
import com.icycraft.mymem.entity.Memory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemDao extends BaseMapper<Memory> {

    @Select("SELECT\n" +
            "\ta.*\n" +
            "FROM\n" +
            "\tmemory a\n" +
            "\tLEFT JOIN user b ON a.user_id = b.id \n" +
            "WHERE\n" +
            "\ta.depth <= b.limit_depth\n" +
            "and a.del_flag=0\n"+
            "ORDER BY RAND() LIMIT 1")
    Memory getRandomMem();

    @Select("SELECT a.* FROM memory a LEFT JOIN user b on a.user_id = b.id WHERE a.depth <=b.limit_depth and a.del_flag = 0 ")
    List<Memory> getLimitMems();


    @Update("update memory set loved_num = loved_num + 1 where id = #{memId}")
    int addLoved(long memId);

    @Update("update memory set loved_num = loved_num - 1 where id = #{memId}")
    int delLoved(long memId);


    @Update("update memory set comment_num = comment_num + 1 where id = #{memId}")
    int addCom(long memId);

    @Update("update memory set comment_num = comment_num - 1 where id = #{memId}")
    int delCom(long memId);


    @Select("SELECT\n" +
            "\ta.*,\n" +
            "\tb.NAME,\n" +
            "\tb.avatar,\n" +
            "\tb.introduce,\n" +
            "\tb.loved_num AS user_loved_num, \n" +
            "\tb.level\n" +
            "FROM\n" +
            "\tmemory a\n" +
            "\tLEFT JOIN user b ON a.user_id = b.id \n" +
            "WHERE\n" +
            "\ta.depth <= b.limit_depth\n" +
            "ORDER BY\n" +
            "\tRAND() \n" +
            "\tLIMIT 300;")
    List<MemEtc> getMemEtcList();

    MemEtc getMemEtcByMemId(long id);


    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tmemory \n" +
            "WHERE\n" +
            "\tuser_id = #{userId}\n" +
            " <if test=\"depth != 10\"> "+
            "and depth = #{depth} "+
            "</if>"+
            "ORDER BY\n" +
            "\tcreate_date\n desc " +
            "LIMIT #{recordStart},5")
    List<Memory> getMemByUserId(long userId,int recordStart,int depth);

    @Select("<script>\n"+
            "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tmemory \n" +
            "WHERE\n" +
            "\tid in (<foreach item='id' index='index' collection='ids'      open='(' separator=',' close=')'>\"\n" +
            "            + \"#{id}\"\n" +
            "</foreach>\n"+
            "ORDER BY\n" +
            "\tcreate_date\n desc " +
            "LIMIT #{recordStart},5"
        +"</script>")
    List<Memory> getLovedMemByUserId(@Param("ids") List<Long> ids, int recordStart, String orderColumn);

}

