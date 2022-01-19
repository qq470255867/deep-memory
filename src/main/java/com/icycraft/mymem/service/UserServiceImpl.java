package com.icycraft.mymem.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.icycraft.mymem.config.LevelConfig;
import com.icycraft.mymem.config.RedisUtil;
import com.icycraft.mymem.dao.UserDao;
import com.icycraft.mymem.entity.User;
import com.icycraft.mymem.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Autowired
    RestTemplate restTemplate;

    @Value("${app.id}")
    private String appId;

    @Value("${app.secret}")
    private String appSecret;

    @Autowired
    LovedService lovedService;

    @Override
    public User getUserById(long id) {

        User user = userDao.selectById(id);

        int userLovedNum = lovedService.getUserLovedNum(id);

        user.setLovedNum(userLovedNum);

        return user;
    }

    @Override
    public String getOpenId(String code) throws Exception {
        String url ="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);

        JSONObject body = JSONObject.parseObject(entity.getBody());
        assert body != null;
        if (StringUtils.isEmpty(body.getString("openid"))){
            throw new Exception("调用登录接口失败");
        }else {
            return body.getString("openid");
        }

    }

    @Override
    public User login(String openId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("wx_id",openId);
        User user = userDao.selectOne(userQueryWrapper);
        if (user==null){
            //首次登录
            User newUser = new User();
            newUser.setAvatar("http://img.duoziwang.com/2019/05/08050202206347.jpg");
            newUser.setWxId(openId);
            newUser.setLevel(1);
            newUser.setName("");
            userDao.insert(newUser);
            return userDao.selectOne(userQueryWrapper);
        }else {
            return user;
        }


    }

    @Override
    public User updateUser(User user) {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();

        userUpdateWrapper.eq("id",user.getId());
        userDao.update(user,userUpdateWrapper);

        return userDao.selectById(user.getId());
    }

    @Override
    public int addExp(long userId, int exp) {
        User user = userDao.selectById(userId);
        int curExp = user.getCurExp();
        curExp = curExp +exp;
        int less = LevelConfig.canUpLv(user.getLevel(), curExp);
        //经验余量

        if (less>=0){
            //可以升级了
            user.setCurExp(less);
            user.setLevel(user.getLevel()+1);
        }else {
            user.setCurExp(curExp);
        }
        userDao.update(user,new QueryWrapper<User>().eq("id",user.getId()));

        return 0;
    }

}
