package com.yuanjun.log.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName:IpUtil
 * @Description : ��ȡԶ��ip�Ĺ�����
 * @author yuanjun
 * @date 2018-1-5
 */
public class IpUtil {
	 /**  
     * ��ȡԶ�̷�������ip��ַ  
     *   
     * ����ʱ�䣺2017��2��24��  
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
