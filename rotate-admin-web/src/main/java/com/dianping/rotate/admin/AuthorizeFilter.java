package com.dianping.rotate.admin;

import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.org.api.PermissionService;
import com.dianping.rotate.org.dto.PermissionType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yangjie on 3/3/15.
 */
public class AuthorizeFilter implements Filter {
    @Autowired
    PermissionService permissionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        int loginId = LoginUtils.getUserLoginIdWithRequest((HttpServletRequest) request);
        if (isDev() ||permissionService.getPermissions(loginId).contains(PermissionType.OPERATOR.value) ) {
            chain.doFilter(request, response);
        } else {
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            httpResponse.setStatus(403);
//            httpResponse.getWriter().print("您没有权限访问该页面");
            request.getRequestDispatcher("not-found.jsp").forward(request,response);
        }
    }

    private boolean isDev() {
        return StringUtils.isNotBlank(System.getProperty("ssoServerName"));
    }

    @Override
    public void destroy() {

    }
}
