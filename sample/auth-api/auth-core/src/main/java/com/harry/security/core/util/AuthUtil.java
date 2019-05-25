package com.harry.security.core.util;

import com.harry.security.core.exception.ValidateCodeException;
import org.springframework.web.context.request.WebRequest;

/**
 * @author harry
 * @version 1.0
 * @title: AuthUtil
 * @description: TODO
 * @date 2019/5/24 21:00
 */
public class AuthUtil {

    public static final String DEVICEID_HEADER_NAME = "deviceId";

    /**
     * 从请求中获取设备ID
     * @param request
     * @return
     */
    public static String getDeviceId(WebRequest request) {
        String deviceId = request.getHeader(DEVICEID_HEADER_NAME);
        if (deviceId == null) {
            throw new ValidateCodeException("缺少deviceId");
        }
        return deviceId;
    }

}
