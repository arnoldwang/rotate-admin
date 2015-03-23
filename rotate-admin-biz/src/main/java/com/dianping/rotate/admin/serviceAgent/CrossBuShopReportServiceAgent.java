package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.admin.vo.SearchShopVo;

import java.util.Map;

/**
 * Created by shenyoujun on 15/3/23.
 */
public interface CrossBuShopReportServiceAgent {

    Map<String, Object> getCrossBuShopReport(SearchShopVo searchShopVo);
}
