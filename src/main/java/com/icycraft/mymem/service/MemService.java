package com.icycraft.mymem.service;

import com.icycraft.mymem.dao.MemDao;
import com.icycraft.mymem.entity.MemEtc;
import com.icycraft.mymem.entity.Memory;
import com.icycraft.mymem.entity.PageList;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

public interface MemService {


     Memory getAMemory();

     Memory getMemoryByKey(int key);

     Memory getMemoryById(long id);

     Memory addAMemory(Memory memory);

     PageList<Memory> getMemoryByUserId(long userId, int page, int depth);

     PageList<Memory> getLovedByUserId(long userId, int page, int depth);

     PageList<MemEtc> getMemoryRand();

     int delMem(long id);

     List<Memory> saveAllToEs();

     PageList<Memory> searchMemory(int page, String content);


     String  saveAllFromFileToMySql();
}
