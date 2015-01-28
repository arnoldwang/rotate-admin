package com.dianping.rotate.admin.controller;

import com.dianping.combiz.util.JsonUtils;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by dev_wzhang on 15-1-20.
 */
@Controller
@RequestMapping("/territory")
//@Log4j
public class TerritoryController {

    @Autowired
    TerritoryServiceAgent territoryServiceAgent;

    /**
     * 删除战区
     *
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/{territoryId}", method = RequestMethod.DELETE)
    @ResponseBody
    public Integer deleteTerritory(@PathVariable int territoryId) {


        int operatorId = LoginUtils.getUserLoginId();
        boolean result = territoryServiceAgent.deleteTerritory(territoryId, operatorId);
        if (!result) {
            throw new ApplicationException("战区删除失败!");
        }
        return territoryId;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Integer create(@RequestBody TerritoryForWebDto territoryForWebDto) throws IOException {

//        TerritoryForWebDto territoryForWebDto = JsonUtils.fromStr(data, TerritoryForWebDto.class);

        territoryForWebDto.setOperatorId(LoginUtils.getUserLoginId());

        Response<Integer>  result = territoryServiceAgent.create(territoryForWebDto);

        if (!result.isSuccess()) throw new ApplicationException("战区创建失败,"+result.getComment());

        return result.getObj();

    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public TerritoryTreeDto loadTerritoryTree() {
        return territoryServiceAgent.loadFullTerritoryTree();
    }

    /**
     * 查询战区面包屑
     *
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryTerritoryBreadCrumbs", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryDto> queryTerritoryBreadCrumbs(@RequestParam int territoryId) {
        return territoryServiceAgent.queryTerritoriyBreadCrumbsByTerritoryId(territoryId);
    }

    /**
     * 查询子站区
     *
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryChildTerritoriesByTerritoryId", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryDto> queryChildTerritoriesByTerritoryId(@RequestParam int territoryId) {
        return territoryServiceAgent.queryChildTerritoriesByTerritoryId(territoryId);
    }

    /**
     * 更新战区
     *
     * @return
     */
    @RequestMapping(value = "/updateTerritory", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateTerritory(@RequestBody TerritoryForWebDto territoryForWebDto) throws IOException {
        territoryForWebDto.setOperatorId(LoginUtils.getUserLoginId());
        Response<Integer> result = territoryServiceAgent.update(territoryForWebDto);
        if(!result.isSuccess()) throw new ApplicationException("战区更新失败,"+result.getComment());
        return result.getObj();
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryDto> queryAllValidTerritory(){
        return  territoryServiceAgent.queryAllValidTerritory();
    }

    @RequestMapping(value = "/base-info", method = RequestMethod.GET)
    @ResponseBody
    public TerritoryForWebDto loadTerritoryBaseInfo(Integer id){
        return  territoryServiceAgent.loadTerritoryInfoForWeb(id);
    }


}
