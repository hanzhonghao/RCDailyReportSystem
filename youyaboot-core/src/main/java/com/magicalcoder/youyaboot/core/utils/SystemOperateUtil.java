package com.magicalcoder.youyaboot.core.utils;

import com.magicalcoder.youyaboot.core.common.constant.SystemOperateEnum;

public class SystemOperateUtil {

    /*操作系统*/
    public static SystemOperateEnum system(){
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) {
            return SystemOperateEnum.MAC;
        } else if (osName.startsWith("Windows")) {
            return SystemOperateEnum.WINDOWS;
        } else {
            return SystemOperateEnum.LINUX;
        }
    }
}
