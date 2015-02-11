package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.admin.dto.TaskInfoDto;
import com.dianping.taskcenter.dto.TaskListRequest;

import java.util.List;

/**
 * Created by shenyoujun on 15/2/9.
 */
public interface TaskCenterServiceAgent {

    List<TaskInfoDto> queryTasks(TaskListRequest taskListRequest);

    int getTaskCount(Integer ownerId,Integer sourceId,Integer status);

    void maskTaskFinish(int taskId);

}
