package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TeamServiceAgent;
import com.dianping.rotate.org.api.TeamService;
import com.dianping.rotate.org.dto.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by shenyoujun on 15/2/10.
 */
@Component
public class TeamServiceAgentImpl implements TeamServiceAgent {

    @Autowired
    private TeamService teamService;


    @Override
    public Team getTeam(Integer teamId) {
        try {
            return teamService.getTeam(teamId);
        } catch (Exception e) {
            throw new ApplicationException("人与组织服务异常,getTeam:"+e.getMessage());
        }
    }
}
