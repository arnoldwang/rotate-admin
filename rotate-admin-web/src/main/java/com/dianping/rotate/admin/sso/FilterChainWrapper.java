package com.dianping.rotate.admin.sso;

import javax.servlet.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 封装一下FilterChain，在默认FilterChain前面再加上一些filter。
 * @author yihua.huang@dianping.com
 */
public class FilterChainWrapper implements FilterChain {

	private final Iterator<Filter> filterIterator;

	private final FilterChain filterChain;

	public FilterChainWrapper(List<Filter> filters, FilterChain filterChain) {
		this.filterChain = filterChain;
		this.filterIterator = filters.iterator();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		if (filterIterator.hasNext()) {
			filterIterator.next().doFilter(request, response, this);
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
