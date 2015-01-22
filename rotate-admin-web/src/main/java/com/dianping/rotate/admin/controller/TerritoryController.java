package com.dianping.rotate.admin.controller;

import com.dianping.combiz.util.JsonUtils;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by dev_wzhang on 15-1-20.
 */
@Controller
@RequestMapping("/territory")
public class TerritoryController {

    @Autowired
    TerritoryServiceAgent territoryServiceAgent;
    /**
     * 删除战区
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody String deleteTerritory(@RequestParam int territoryId,@RequestParam int operatorId){

        boolean result = territoryServiceAgent.deleteTerritory(territoryId,operatorId);
        if(!result){
            throw new ApplicationException("战区删除失败!");
        }
        return StringUtils.EMPTY;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Integer create(@RequestParam("data") String data) throws IOException {

        TerritoryForWebDto territoryForWebDto = JsonUtils.fromStr(data, TerritoryForWebDto.class);

        territoryForWebDto.setOperatorId(LoginUtils.getUserLoginId());

        Integer result = territoryServiceAgent.create(territoryForWebDto);

        if(result==null) throw new ApplicationException("战区创建失败");

        return result;

    }

    @RequestMapping(value = "/loadTerritoryTree", method = RequestMethod.GET)
    @ResponseBody
    public TerritoryTreeDto loadTerritoryTree(){
        return territoryServiceAgent.loadFullTerritoryTree();
    }

    /**
     * 查询战区面包屑
     * @param territoryId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryTerritoryBreadCrumbs", method = RequestMethod.POST)
    @ResponseBody
    public List<TerritoryDto> queryTerritoryBreadCrumbs(@RequestParam int territoryId){
        return territoryServiceAgent.queryTerritoriyBreadCrumbsByTerritoryId(territoryId);
    }

    /**
     * 查询子站区
     * @param territoryId
     * @return
     */
    @RequestMapping(value = "/queryChildTerritoriesByTerritoryId", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryDto> queryChildTerritoriesByTerritoryId(@RequestParam int territoryId){
        return  territoryServiceAgent.queryChildTerritoriesByTerritoryId(territoryId);
    }

    /**
     * 更新战区
     * @param territoryForWebDto
     * @return
     */
    @RequestMapping(value = "/updateTerritory", method = RequestMethod.POST)
    @ResponseBody
    public String updateTerritory(@RequestParam TerritoryForWebDto territoryForWebDto){
        return  StringUtils.EMPTY;
    }

    @RequestMapping(value = "/queryAllValidTerritory", method = RequestMethod.GET)
    @ResponseBody
    public List<TerritoryDto> queryAllValidTerritory(){
        return  territoryServiceAgent.queryAllValidTerritory();
    }

}
