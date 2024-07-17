package com.mynacos.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import cn.hutool.core.util.StrUtil;

public class NetUtils {
    
    private static String localIp;
    
    /**
     * Get local ip.
     *
     * @return local ip
     */
    public static String localIP() {
        try {
            if (!StrUtil.isEmpty(localIp)) {
                return localIp;
            }
            
            String ip = System.getProperty("com.alibaba.nacos.client.naming.local.ip",
                    InetAddress.getLocalHost().getHostAddress());
            
            return localIp = ip;
        } catch (UnknownHostException e) {
            return "resolve_failed";
        }
    }
}