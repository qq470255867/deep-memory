package com.icycraft.mymem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.mymem.entity.User;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao  extends BaseMapper<User> {

    @Update("update user set loved_num = loved_num + 1 where id = #{userId}")
    int addLoved(long userId);

    @Update("update user set loved_num = loved_num - 1 where id = #{userId}")
    int delLoved(long userId);


}
