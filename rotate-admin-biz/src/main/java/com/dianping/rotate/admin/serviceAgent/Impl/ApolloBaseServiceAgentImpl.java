package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.apollobase.api.Constants;
import com.dianping.apollobase.api.DepartmentGroupService;
import com.dianping.apollobase.api.Group;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import com.google.common.collect.Maps;
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
    public Map<Integer, Group> getBizGroups() {
        try {
            List<Group> groups = departmentGroupService.getGroupsOfType(Constants.GROUP_TYPE_ROTATE_BIZ);

            Map<Integer, Group> result = Maps.newHashMap();

            if(CollectionUtils.isEmpty(groups)) return result;

            for(Group group:groups){
                result.put(group.getGroupId(), group);
            }
            return result;
        } catch (Exception e) {
            throw new ApplicationException("获取BIZ信息失败,ApolloBaseServiceAgent.getAllBizInfo"+e.getMessage());
        }
    }

}
