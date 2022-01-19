package com.icycraft.mymem.service;

import com.icycraft.mymem.dao.PictureRepository;
import com.icycraft.mymem.entity.Picture;
import com.icycraft.mymem.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicServiceImpl implements PicService{


    @Autowired
    PictureRepository pictureRepository;


    @Override
    public List<Picture> getPicturesSame(String content) {

        PageRequest pr = PageRequest.of(1, 20);

        Page<Picture> byContent = pictureRepository.findByContent(StringUtils.termString(content), pr);

        return byContent.getContent();
    }
}
