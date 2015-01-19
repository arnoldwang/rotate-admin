package com.dianping.rotate.admin.sso;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

/**
 * 使用map保存FilterConfig内容
 * @author yihua.huang@dianping.com
 */
public class MapBasedFilterConfig implements FilterConfig {

	private final Map<String,String> map;

	private final String filterName;

	private final ServletContext servletContext;

	public MapBasedFilterConfig(Map<String, String> map, String filterName, ServletContext servletContext) {
		this.map = map;
		this.filterName = filterName;
		this.servletContext = servletContext;
	}

	@Override
	public String getFilterName() {
		return filterName;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public String getInitParameter(String name) {
		return map.get(name);
	}

	@Override
	public Enumeration getInitParameterNames() {
		return Collections.enumeration(map.keySet());
	}
}
