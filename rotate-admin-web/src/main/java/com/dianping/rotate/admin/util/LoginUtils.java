package com.dianping.rotate.admin.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 * Created by jrose on 10/27/14.
 */
public class LoginUtils {

    /**
     * 获取用户信息Id
     * @return
     */
    public static Integer getUserLoginId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return 0;
        }

        return getUserLoginIdWithRequest(attributes.getRequest());
    }

    public static Integer getUserLoginIdWithRequest(HttpServletRequest req) {

        String userInfoStr = req.getRemoteUser();
        String[] infos = userInfoStr.split("\\|");
        try {
            return Integer.parseInt(infos[1]);
        } catch (Exception e) {
            return 0;
        }
    }
}
