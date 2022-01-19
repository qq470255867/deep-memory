package com.icycraft.mymem.service;

import com.icycraft.mymem.dao.OpLogDao;
import com.icycraft.mymem.entity.OpLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class OpLogServiceImpl implements OpLogService{

    @Autowired
    OpLogDao opLogDao;




    @Override
    public OpLog addLog(OpLog opLog) {
        opLog.setDate(new Date());
        opLogDao.insert(opLog);
        return opLog;
    }
}
