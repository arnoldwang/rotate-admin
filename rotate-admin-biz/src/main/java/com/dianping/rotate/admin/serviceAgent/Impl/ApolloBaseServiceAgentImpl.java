package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.apollobase.api.Constants;
import com.dianping.apollobase.api.DepartmentGroupService;
import com.dianping.apollobase.api.Group;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Component
@Log4j
public class ApolloBaseServiceAgentImpl implements ApolloBaseServiceAgent {

    @Autowired
    private DepartmentGroupService departmentGroupService;

    @Override
    public List<Map<String,Object>> getAllBizInfo(){

        try {
            List<Group> groups = departmentGroupService.getGroupsOfType(Constants.GROUP_TYPE_ROTATE_BIZ);

            List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();

            if(CollectionUtils.isEmpty(groups)) return result;

            for(Group group:groups){
                Map<String,Object> resultMap = new HashMap<String, Object>();
                resultMap.put("value",group.getGroupId());
                resultMap.put("text",group.getGroupName());
                result.add(resultMap);
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException("获取BIZ信息失败,ApolloBaseServiceAgent.getAllBizInfo"+e.getMessage());
        }
    }


}
