package com.icycraft.mymem.util;

public class StringUtils {





    public static String termString(String content){
        String filterItem = "是,是的,不,如何,一个人,什么,没,有,能,怎么,你,我,个,人,以,已经,已,听,说";
        String[] items = filterItem.split(",");
        String filterdContent = content;
        for (String item : items) {
            filterdContent = filterdContent.replace(item, "");
        }
        return filterdContent;
    }
}
