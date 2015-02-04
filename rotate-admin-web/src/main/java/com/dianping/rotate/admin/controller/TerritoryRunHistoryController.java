package com.dianping.rotate.admin.controller;

import com.dianping.core.type.PageModel;
import com.dianping.rotate.admin.serviceAgent.TerritoryRunHistoryServiceAgent;
import com.dianping.rotate.admin.serviceAgent.TerritoryRunStatusServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.territory.dto.TerritoryRunHistoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryRunStatusForWebDto;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by shenyoujun on 15/2/3.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryRunHistoryController {

    @Autowired
    private TerritoryRunHistoryServiceAgent territoryRunHistoryServiceAgent;

    @Autowired
    private TerritoryRunStatusServiceAgent territoryRunStatusServiceAgent;

    @RequestMapping(value = "/historyList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryTerritoryRunHistory(
            @RequestParam int pageSize,
            @RequestParam int pageIndex) {

        Map<String,Object> result = new HashMap<String, Object>();

        int total = 0;
        List<TerritoryRunHistoryForWebDto> resultList = Lists.newArrayList();

        result.put("total",total);
        result.put("items",resultList);

        PageModel pageModel = territoryRunHistoryServiceAgent.queryTerritoryRunHistoryLists(pageIndex,pageSize);

        if(pageModel==null) return result;

        List<TerritoryRunHistoryForWebDto> historyLists =
                (List<TerritoryRunHistoryForWebDto>) pageModel.getRecords();

        if(CollectionUtils.isEmpty(historyLists)) return result;

        total = pageModel.getRecordCount();
        resultList=historyLists;

        result.put("total",total);
        result.put("items",resultList);

        return result;

    }

    @RequestMapping(value = "/leafHistoryList", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryRunStatusForWebDto> queryLeafHistoryList(@RequestParam Integer runHistoryId) {

        return territoryRunStatusServiceAgent.getTerritoryRunListsByHistoryId(runHistoryId);


    }

    @RequestMapping(value = "/reRunTerritoryRule", method = RequestMethod.GET)
    @ResponseBody
    public String reRunTerritoryRule(@RequestParam Integer id) {

        territoryRunStatusServiceAgent.reRunTerritoryRule(id, LoginUtils.getUserLoginId());
        return "设置成功";
    }



}
