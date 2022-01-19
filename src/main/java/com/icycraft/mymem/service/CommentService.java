package com.icycraft.mymem.service;

import com.icycraft.mymem.entity.Comment;
import com.icycraft.mymem.entity.PageList;

public interface CommentService {


    Comment addComment(Comment comment);

    int delComment(long id);

    PageList<Comment> getComments(int start,long memId);


    int getCommentNum(long memId);


    int addCommentNum(long memId);

}
