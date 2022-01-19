package com.icycraft.mymem.config;

import org.apache.commons.lang3.StringUtils;

public class RedisUtil {

    public final static String MEMS = "mems";

    public final static String USER_LOVED_NUM = "user_loved_num";

    public final static String MEM_LOVED_NUM = "mem_loved_num";

    public final static String MEM_COM_NUM = "mem_com_num";


    public static int RE_NUM(String num){
        if (StringUtils.isEmpty(num)){
            return 0;
        }else {
            return Integer.parseInt(num);
        }
    }

}
