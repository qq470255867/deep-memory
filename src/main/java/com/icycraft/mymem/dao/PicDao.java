package com.icycraft.mymem.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.icycraft.mymem.entity.Picture;
import org.springframework.stereotype.Repository;

@Repository
public interface PicDao   extends BaseMapper<Picture> {
}
