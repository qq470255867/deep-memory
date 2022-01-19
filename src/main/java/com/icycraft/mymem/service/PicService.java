package com.icycraft.mymem.service;

import com.icycraft.mymem.entity.Picture;

import java.util.List;

public interface PicService {


    List<Picture> getPicturesSame(String content);
}
