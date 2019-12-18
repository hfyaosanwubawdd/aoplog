package com.yuanjun.log.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName:IpUtil
 * @Description : 获取远程ip的工具类
 * @author yuanjun
 * @date 2018-1-5
 */
public class IpUtil {
	 /**  
     * 获取远程访问主机ip地址  
     *   
     * 创建时间：2017年2月24日  
     *   
     * @author HY  
     * @param request  
     * @return  
     */  
    public static String getRemortIP(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
}
