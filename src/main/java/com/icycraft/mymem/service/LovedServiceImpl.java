package com.icycraft.mymem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.mymem.config.RedisUtil;
import com.icycraft.mymem.dao.LovedDao;
import com.icycraft.mymem.dao.MemDao;
import com.icycraft.mymem.dao.UserDao;
import com.icycraft.mymem.entity.Loved;
import com.icycraft.mymem.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LovedServiceImpl implements LovedService{

    @Autowired
    private LovedDao lovedDao;

    @Autowired
    MemDao memDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RedisOperator redisOperator;

    @Override
    public Loved addLoved(Loved loved) {

        lovedDao.insert(loved);

        String userLovedNum = redisOperator.hget(RedisUtil.USER_LOVED_NUM, loved.getMemUserId());

        String addNum = lovedAdd(userLovedNum);

        redisOperator.hset(RedisUtil.USER_LOVED_NUM, loved.getMemUserId(),addNum);

        String memLovedNum = redisOperator.hget(RedisUtil.MEM_LOVED_NUM, loved.getMemId() + "");

        redisOperator.hset(RedisUtil.MEM_LOVED_NUM, loved.getMemId()+"",lovedAdd(memLovedNum));

        return loved;
    }

    @Override
    public Loved delLoved(Loved loved) {


        String userLovedNum = redisOperator.hget(RedisUtil.USER_LOVED_NUM, loved.getMemUserId());

        redisOperator.hset(RedisUtil.USER_LOVED_NUM, loved.getMemUserId(),lovedDel(userLovedNum));

        String memLovedNum = redisOperator.hget(RedisUtil.MEM_LOVED_NUM, loved.getMemId() + "");

        redisOperator.hset(RedisUtil.MEM_LOVED_NUM, loved.getMemId()+"",lovedDel(memLovedNum));

        QueryWrapper<Loved> qw = new QueryWrapper<>();

        qw.eq("user_id",loved.getUserId()).eq("mem_id",loved.getMemId());
        lovedDao.delete(qw);
        return loved;

    }

    @Override
    public Loved getLoved(Loved loved) {

        QueryWrapper<Loved> qw = new QueryWrapper<>();

        qw.eq("user_id",loved.getUserId()).eq("mem_id",loved.getMemId());

        return lovedDao.selectOne(qw);
    }


    @Override
    public int getMemLovedNum(long memId) {

        String num = redisOperator.hget(RedisUtil.MEM_LOVED_NUM, memId + "");

        if (StringUtils.isEmpty(num)){
            return 0;
        }else {
            return Integer.parseInt(num);
        }
    }

    @Override
    public int getUserLovedNum(long userId) {
        String num = redisOperator.hget(RedisUtil.USER_LOVED_NUM, userId + "");

        if (StringUtils.isEmpty(num)){
            return 0;
        }else {
            return Integer.parseInt(num);
        }

    }


    String lovedAdd(String num){
        if (StringUtils.isEmpty(num)){
            num = "0";
        }
        int i = Integer.parseInt(num);
        i++;
        return i+"";
    }

    String lovedDel(String num){
        if (StringUtils.isEmpty(num)){
            num = "0";
        }

        int i = Integer.parseInt(num);
        if (i>0){
            i--;
        }
        return i+"";
    }

}
