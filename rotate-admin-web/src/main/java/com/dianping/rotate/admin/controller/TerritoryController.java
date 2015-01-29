package com.dianping.rotate.admin.controller;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TerritoryServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.admin.utils.JsonUtils;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.dianping.rotate.territory.dto.TerritoryForWebDto;
import com.dianping.rotate.territory.dto.TerritoryRuleItemDto;
import com.dianping.rotate.territory.dto.TerritoryTreeDto;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by dev_wzhang on 15-1-20.
 */
@Controller
@RequestMapping("/territory")
//@Log4j
public class TerritoryController {

    @Autowired
    TerritoryServiceAgent territoryServiceAgent;

    @Autowired
    UserService userService;

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
    public Integer create(@RequestBody String data) throws IOException {
        TerritoryForWebDto territoryForWebDto      = JsonUtils.fromStr(data, TerritoryForWebDto.class);

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
    public Integer updateTerritory(@RequestBody String data) throws IOException {

        TerritoryForWebDto territoryForWebDto      = JsonUtils.fromStr(data, TerritoryForWebDto.class);
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
    public Object loadTerritoryBaseInfo(Integer id){

        TerritoryForWebDto t = territoryServiceAgent.loadTerritoryInfoForWeb(id);
        if(t==null){
            throw new ApplicationException("不存在战区%s",id);
        }
        Map result = Maps.newHashMap();
        result.put("id",t.getId());
        result.put("bizId",t.getBizId());
        result.put("territoryName",t.getTerritoryName());
        result.put("parentTerritoryId",t.getParentTerritoryId());
        result.put("chiefLeaderId",t.getChiefLeaderId());
        result.put("notOnlineMutGroupCountLimit",t.getNotOnlineMutGroupCountLimit());
        result.put("notOnlineSingleGroupCountLimit",t.getNotOnlineSingleGroupCountLimit());
        if(t.getParentTerritoryId()!=null){
            TerritoryForWebDto parent = territoryServiceAgent.loadTerritoryInfoForWeb(t.getParentTerritoryId());
            if(parent==null){
                throw new ApplicationException("找不到父战区%s",t.getParentTerritoryId());
            }
            result.put("parentTerritoryName",parent.getTerritoryName());
        }

        if(t.getChiefLeaderId()!=null){
            result.put("chiefLeaderName", userService.queryUserByLoginID(t.getChiefLeaderId()).getRealName());
        }

        return  result;
    }




}
