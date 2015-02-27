package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.ba.base.organizationalstructure.api.organization.OrganizationService;
import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.pigeon.remoting.common.exception.InvalidParameterException;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.framework.BeanMappingService;
import com.dianping.rotate.admin.serviceAgent.VirtualTeamServiceAgent;
import com.dianping.rotate.admin.vo.TeamMemberVo;
import com.dianping.rotate.admin.vo.VirtualTeamVo;
import com.dianping.rotate.org.api.TeamService;
import com.dianping.rotate.org.api.TigerTeamService;
import com.dianping.rotate.org.dto.Team;
import com.dianping.rotate.org.dto.TigerTeamDto;
import com.dianping.rotate.org.enums.StatusEnum;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.api.TeamTerritoryService;
import com.dianping.rotate.territory.dto.TerritoryDto;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
@Component
public class VirtualTeamServiceAgentImpl implements VirtualTeamServiceAgent {

    @Autowired
    private TigerTeamService tigerTeamService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamTerritoryService teamTerritoryService;


    @Autowired
    private UserService userService;

    @Override
    public List<VirtualTeamVo> queryVirtualTeamList(Integer bizId) {
        Response<List<TigerTeamDto>> response = tigerTeamService.queryTigerTeamsByBiz(bizId);

        if (!response.isSuccess()) {
            throw new ApplicationException(response.getComment());
        }

        List<TigerTeamDto> tigerTeamDtoList = response.getObj();
        if (CollectionUtils.isEmpty(tigerTeamDtoList)) {
            return Lists.newArrayList();
        }

        List<VirtualTeamVo> virtualTeamVoList = Lists.newArrayList();
        for (TigerTeamDto item : tigerTeamDtoList) {
            VirtualTeamVo vo = beanMappingService.transform(item, VirtualTeamVo.class);
            vo.setTeamType(1);
            //获取父节点名称
            Team team = teamService.getTeam(item.getParentTeamId());
            if (team != null) {
                vo.setParentTeamName(team.getTeamName());
            }

            //获取战区ID和名称
            TerritoryDto territory = teamTerritoryService.getTerritoryByTeamId(item.getId());
            if (territory != null) {
                vo.setTerritoryId(territory.getId());
                vo.setTerritoryName(territory.getTerritoryName());
            }

            //获取组长
            UserDto userDto = userService.queryUserByLoginID(item.getTeamLeaderId());
            if (userDto != null) {
                vo.setTeamLeaderName(userDto.getRealName());
            }
            virtualTeamVoList.add(vo);
        }


        return virtualTeamVoList;
    }

    @Override
    public int saveVirtualTeam(VirtualTeamVo virtualTeamVo, int operatorId) {

        TigerTeamDto tigerTeamDto = beanMappingService.transform(virtualTeamVo, TigerTeamDto.class);
        if (tigerTeamDto == null) {
            throw new InvalidParameterException("参数错误!");
        }
        tigerTeamDto.setStatus(StatusEnum.VALID.getCode());//置为有效

        List<Integer> teamMembers = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(virtualTeamVo.getMembers())) {
            for (TeamMemberVo item : virtualTeamVo.getMembers()) {
                teamMembers.add(item.getMemberId());
            }
        }
        tigerTeamDto.setTeamMembers(teamMembers);

        //save team
        Response<Integer> responseTiger = tigerTeamService.saveTigerTeam(tigerTeamDto, operatorId);
        if (!responseTiger.isSuccess()) {//保存失败
            throw new ApplicationException(responseTiger.getComment());
        }
        if (responseTiger.getObj() == 0) {//没有返回组id
            throw new ApplicationException("保存异常,未获取到teamId!");
        }

        //bind team territory
        Response responseTeamTerritory = teamTerritoryService.bind(responseTiger.getObj(), virtualTeamVo.getTerritoryId());
        if (!responseTeamTerritory.isSuccess()) {
            throw new ApplicationException(responseTeamTerritory.getComment());
        }

        return responseTiger.getObj();
    }

    @Override
    public VirtualTeamVo getVirtualTeam(Integer teamId) {
        //tigerTeamService.
        return null;
    }
}
