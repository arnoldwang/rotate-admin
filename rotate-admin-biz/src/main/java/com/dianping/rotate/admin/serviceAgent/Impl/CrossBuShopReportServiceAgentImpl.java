package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.core.type.PageModel;
import com.dianping.customer.data.api.CrossBuShopReportService;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.CrossBuShopReportServiceAgent;
import com.dianping.rotate.admin.vo.SearchShopVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/3/23.
 */
@Component
public class CrossBuShopReportServiceAgentImpl implements CrossBuShopReportServiceAgent {

    @Autowired
    CrossBuShopReportService crossBuShopReportService;

    @Override
    public Map<String, Object> getCrossBuShopReport(SearchShopVo searchShopVo) {

        if(searchShopVo==null) throw new ApplicationException("输入参数有误，请重新输入");

        if(CollectionUtils.isEmpty(searchShopVo.getBus()) || searchShopVo.getBizId() ==null) throw new ApplicationException("请选择BizId");

        try {
            Map<String, Object> conditions = gererateConditions(searchShopVo.getBizId(), searchShopVo.getBus());

            PageModel pageModel = crossBuShopReportService.getCrossBuShopReport(conditions,searchShopVo.getPageIndex(),searchShopVo.getPageSize());

            Map<String,Object> result = new HashMap<String, Object>();

            if(pageModel!=null){
                result.put("items", pageModel.getRecords());
                result.put("total", pageModel.getRecordCount());
            }

            return result;
        } catch (Exception e) {
            throw new ApplicationException("dataService异常 getCrossBuShopReport"+e.getMessage());
        }
    }

    private Map<String, Object> gererateConditions(Integer bizId, List<Integer> bus) {
        Map<String,Object> conditions = new HashMap<String, Object>();

        conditions.put("bizId",bizId);

        conditions.put("sales_biz_id",bus);
        return conditions;
    }
}
