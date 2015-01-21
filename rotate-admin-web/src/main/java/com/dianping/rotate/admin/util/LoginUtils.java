package com.dianping.rotate.admin.util;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.model.UserProfile;
import com.dianping.rotate.admin.utils.Beans;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息
 * Created by jrose on 10/27/14.
 */
public class LoginUtils {

    /**
     * 获取用户信息
     * @return
     */
    public static UserProfile getUserProfile() {
        HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        OAuthUtil oAuthUtil = Beans.getBean(OAuthUtil.class);
        UserProfile authorization = oAuthUtil.getUserProfile(req.getHeader("Authorization"));
        if (authorization == null) {
            throw new ApplicationException("用户未登录");
        }
        return authorization;
    }

    /**
     * 获取用户信息Id
     * @return
     */
    public static Integer getUserLoginId() {
        UserProfile authorization = getUserProfile();
        return authorization.getLoginId();
    }
}
