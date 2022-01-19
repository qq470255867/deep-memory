package com.icycraft.mymem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.icycraft.mymem.config.LevelConfig;
import com.icycraft.mymem.config.RedisUtil;
import com.icycraft.mymem.dao.CommentDao;
import com.icycraft.mymem.dao.MemDao;
import com.icycraft.mymem.entity.Comment;
import com.icycraft.mymem.entity.PageList;
import com.icycraft.mymem.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentDao commentDao;

    @Autowired
    MemDao memDao;

    @Autowired
    RedisOperator redisOperator;

    @Override
    public Comment addComment(Comment comment) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        comment.setCreateDate(date);
        commentDao.insert(comment);

        memDao.addCom(comment.getMemId());

        addCommentNum(comment.getMemId());

        return comment;
    }

    @Override
    public int delComment(long id) {
        commentDao.deleteById(id);
       // memDao.delCom(comment.getMemId());

        return 0;
    }

    @Override
    public PageList<Comment> getComments(int start, long memId) {



        List<Comment> comments = commentDao.getComments(memId, start);
        PageList<Comment> result = new PageList<>();
        comments.forEach(comment -> comment.setLevelColor(LevelConfig.getLVColor(comment.getLevel())));

        result.setList(comments);
        Integer count = commentDao.selectCount(new QueryWrapper<Comment>().eq("mem_id", memId));
        result.setCount(count);
        return result;
    }

    @Override
    public int getCommentNum(long memId) {

        String num = redisOperator.hget(RedisUtil.MEM_COM_NUM, memId + "");

        return RedisUtil.RE_NUM(num);
    }

    @Override
    public int addCommentNum(long memId) {

        String num = redisOperator.hget(RedisUtil.MEM_COM_NUM, memId + "");

        int i = RedisUtil.RE_NUM(num);

        i++;

        redisOperator.hset(RedisUtil.MEM_COM_NUM,memId+"",i+"");

        return i;
    }


}
