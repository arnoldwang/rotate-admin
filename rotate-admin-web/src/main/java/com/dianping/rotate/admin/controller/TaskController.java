package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.dto.TaskInfoDto;
import com.dianping.rotate.admin.serviceAgent.TaskCenterServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.taskcenter.constants.Source;
import com.dianping.taskcenter.dto.TaskListRequest;
import com.dianping.taskcenter.enums.SortByEnum;
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
 * Created by shenyoujun on 15/2/10.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskCenterServiceAgent taskCenterServiceAgent;

    @RequestMapping(value = "/taskList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryTerritoryRunHistory(
            @RequestParam int pageSize,
            @RequestParam int pageIndex,
            @RequestParam Boolean taskValue) {

        TaskListRequest queryDto = new TaskListRequest();

        queryDto.setSourceId(Source.CUSTOMER_ROTATE_TERRITORY);
        queryDto.setMaxsize(pageSize);
        queryDto.setOffset((pageIndex - 1) == 0 ? 0 : (pageIndex - 1) * pageSize + 1);
        queryDto.setOwnerId(LoginUtils.getUserLoginId());
        queryDto.setSortBy(SortByEnum.STATUS.getSort());
        queryDto.setStatus(taskValue == Boolean.TRUE ? 0 : -1);

        List<TaskInfoDto> resultList = taskCenterServiceAgent.queryTasks(queryDto);

        int total = taskCenterServiceAgent.getTaskCount(LoginUtils.getUserLoginId(),
                Source.CUSTOMER_ROTATE_TERRITORY, taskValue == Boolean.TRUE ? 0 : -1);


        Map<String, Object> result = new HashMap<String, Object>();

        result.put("total", total);
        result.put("items", resultList);
        return result;
    }

    @RequestMapping(value = "/tag",method = RequestMethod.GET)
    @ResponseBody
    public String tagTag(
            @RequestParam int id) {
        taskCenterServiceAgent.maskTaskFinish(id);

        String result = "标记任务成功";

        return result;
    }
}
