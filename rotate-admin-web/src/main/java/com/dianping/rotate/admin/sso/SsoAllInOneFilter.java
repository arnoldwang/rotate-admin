package com.dianping.rotate.admin.sso;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.authentication.Saml11AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Saml11TicketValidationFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * SSO配置太多啦，不如合并成一个吧，顺便加上本地调试之类的功能
 *
 * @author yihua.huang@dianping.com
 */
public class SsoAllInOneFilter implements Filter {

	private List<Filter> filterList;

	private Pattern excludePattern;

	private Map<String, String> filterConfigs;

	public SsoAllInOneFilter() {
		filterList = new ArrayList<Filter>(4);
		filterList.add(new SingleSignOutFilter());
		filterList.add(new Saml11AuthenticationFilter());
		filterList.add(new Saml11TicketValidationFilter());
		filterList.add(new HttpServletRequestWrapperFilter());
		filterConfigs = new HashMap<String, String>();
		initDefaultConfigs();

	}

	private void initDefaultConfigs() {
		//默认配置，如果filter的initParam有设置则会覆盖它
		filterConfigs.put("artifactParameterName", "SAMLart");
		filterConfigs.put("casServerLoginUrlLionKey", "cas-server-webapp.loginUrl");
		filterConfigs.put("casServerUrlPrefixLionKey", "cas-server-webapp.serverName");
		filterConfigs.put("redirectAfterValidation", "true");
		filterConfigs.put("tolerance", "5000");
	}

	private void addOverrideConfigs() {
		//额外配置，这些配置的优先级最高
		if (StringUtils.isNotBlank(System.getProperty("ssoServerName"))) {
			filterConfigs.put("serverName", System.getProperty("ssoServerName"));
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initByConfig(filterConfig);
		Enumeration initParameterNames = filterConfig.getInitParameterNames();
		while (initParameterNames.hasMoreElements()) {
			String name = (String) initParameterNames.nextElement();
			filterConfigs.put(name, filterConfig.getInitParameter(name));
		}
		addOverrideConfigs();
		for (Filter filter : filterList) {
			filter.init(new MapBasedFilterConfig(filterConfigs, filterConfig.getFilterName(),
					filterConfig.getServletContext()));
		}
	}

	private void initByConfig(FilterConfig filterConfig) {
		if (filterConfig.getInitParameter("excludeUrl") != null) {
			excludePattern = Pattern.compile(filterConfig.getInitParameter("excludeUrl"));
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (excludePattern != null && excludePattern.matcher(httpServletRequest.getRequestURI()).matches()) {
            chain.doFilter(request, response);
        } else {
            new FilterChainWrapper(filterList, chain).doFilter(request, response);
        }
	}

	@Override
	public void destroy() {
		for (Filter filter : filterList) {
			filter.destroy();
		}
	}
}
