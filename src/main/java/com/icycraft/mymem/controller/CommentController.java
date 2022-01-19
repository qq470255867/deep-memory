package com.icycraft.mymem.controller;

import com.icycraft.mymem.entity.Comment;
import com.icycraft.mymem.entity.PageList;
import com.icycraft.mymem.entity.WebResult;
import com.icycraft.mymem.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/com/")
@Slf4j
public class CommentController {



    @Autowired
    CommentService commentService;



    @RequestMapping("get/{memId}/{page}")
    public WebResult getComments(@PathVariable("memId") long memId,@PathVariable("page") int page) {

        try {
            int start = page*5;

            PageList<Comment> comments = commentService.getComments(start, memId);

            return WebResult.SUCCESS(comments);

        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }

    }

    @PostMapping("add")
    public WebResult addComment(@RequestBody Comment comment) {

        try {
            Comment comment1 = commentService.addComment(comment);
            return WebResult.SUCCESS(comment1);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return WebResult.ERROR(e.getMessage());
        }
    }





}
