package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 获取UUID
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
