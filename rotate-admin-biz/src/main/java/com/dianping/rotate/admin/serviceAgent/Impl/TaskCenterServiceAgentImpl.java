package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.rotate.admin.constant.Message;
import com.dianping.rotate.admin.dto.TaskInfoDto;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.TaskCenterServiceAgent;
import com.dianping.rotate.admin.serviceAgent.TeamServiceAgent;
import com.dianping.rotate.admin.utils.MessageAssembleUtils;
import com.dianping.rotate.org.dto.Team;
import com.dianping.rotate.smt.dto.Response;
import com.dianping.rotate.territory.api.OrgChangeHistoryService;
import com.dianping.rotate.territory.dto.OrgChangeHistoryDto;
import com.dianping.taskcenter.TaskCenterService;
import com.dianping.taskcenter.constants.Category;
import com.dianping.taskcenter.constants.Source;
import com.dianping.taskcenter.dto.TaskDTO;
import com.dianping.taskcenter.dto.TaskListRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/2/9.
 */
@Component
public class TaskCenterServiceAgentImpl implements TaskCenterServiceAgent {

    @Autowired
    private TaskCenterService taskCenterService;

    @Autowired
    private TeamServiceAgent teamServiceAgent;

    @Autowired
    private OrgChangeHistoryService orgChangeHistoryService;

    private static final int DEFAULT_QUERY_ID = -1;

    @Override
    public List<TaskInfoDto> queryTasks(TaskListRequest taskListRequest) {

        try {
            List<TaskDTO> taskDTOs = taskCenterService.getTasksByTaskRequest(taskListRequest);

            if (CollectionUtils.isEmpty(taskDTOs)) return Collections.emptyList();

            List<TaskInfoDto> result = Lists.newArrayListWithExpectedSize(taskDTOs.size());
            Map<Integer, Team> teamMap = Maps.newHashMap();

            for (TaskDTO taskDTO : taskDTOs) {
                if (taskDTO == null || taskDTO.getOutBizId() == null) continue;
                generateTaskDto(result, teamMap, taskDTO);
            }

            return result;
        } catch (Exception e) {
            throw new ApplicationException("任务中心服务异常,queryTasks:"+e.getMessage());
        }
    }

    private void generateTaskDto(List<TaskInfoDto> result, Map<Integer, Team> teamMap, TaskDTO taskDTO) {
        TaskInfoDto taskInfoDto = new TaskInfoDto();

        taskInfoDto.setCreateTime(taskDTO.getAddTime());
        taskInfoDto.setId(taskDTO.getId());

        try {
            Integer teamId = Integer.parseInt(taskDTO.getOutBizId());

            if (taskDTO.getCategoryId() == Category.ORG_CREATE) {
                Team team = getTeam(teamMap, teamId);
                String content = MessageAssembleUtils.assembleMessageWithTeam(Message.ORG_CREATE,
                        Message.NONE_TEAM_NAME,team);
                taskInfoDto.setContent(content);
                result.add(taskInfoDto);
            } else if (taskDTO.getCategoryId() == Category.ORG_CHANGE) {
                //如果是父节点变更，bizId存的是orgChangeHistory的主键，通过orgChangeHistoryService拿到父节点变更历史
                Response<OrgChangeHistoryDto> response = orgChangeHistoryService.findById(teamId);

                if (response.isSuccess()) {
                    OrgChangeHistoryDto historyChange = response.getObj();
                    generateOrgChangeContent(historyChange, taskInfoDto, teamMap);
                }
                taskInfoDto.setContent(response.getComment());
            }


            result.add(taskInfoDto);
        } catch (NumberFormatException e) {
            taskInfoDto.setContent(MessageAssembleUtils.assembleErrorMessage(Message.ERROR_MSG,"解析内容失败"));
        }
    }

    private Team getTeam(Map<Integer, Team> teamMap, Integer teamId) {

        if(teamId == null) return null;

        Team team = teamMap.get(teamId);
        if(team==null){
            team = getTeamNotInCache(teamId, teamMap);
        }
        return team;
    }

    private Team getTeamNotInCache(Integer teamId, Map<Integer, Team> teamMap) {
        Team team = teamServiceAgent.getTeam(teamId);
        if (team == null || team.getTeamName() == null) {
            return null;
        } else {
            teamMap.put(teamId, team);
            return team;
        }
    }

    private void generateOrgChangeContent(OrgChangeHistoryDto historyChange, TaskInfoDto taskInfoDto, Map<Integer, Team> teamMap) {

        if (historyChange == null) {
            String content = MessageAssembleUtils.assembleErrorMessage(Message.ERROR_MSG, Message.NONE_ORG_HISTORY);
            taskInfoDto.setContent(content);
            return;
        }

        Team team = getTeam(teamMap,historyChange.getOrganizationId());
        Team preSupOrgTeam = getTeam(teamMap,historyChange.getPreSupOrgId());
        Team curSupOrgTeam = getTeam(teamMap,historyChange.getCurSupOrgId());

        String content = MessageAssembleUtils.assembleMessageWithTeam(Message.ORG_CHANGE,
                Message.NONE_TEAM_NAME,team,preSupOrgTeam,curSupOrgTeam);
        taskInfoDto.setContent(content);
    }



    @Override
    public int getTaskCount(int ownerId) {
        try {
            return taskCenterService.getTaskCount(ownerId,DEFAULT_QUERY_ID, Source.CUSTOMER_ROTATE_TERRITORY,DEFAULT_QUERY_ID);
        } catch (Exception e) {
            throw new ApplicationException("任务中心服务异常,getTaskCount:"+e.getMessage());
        }
    }
}
