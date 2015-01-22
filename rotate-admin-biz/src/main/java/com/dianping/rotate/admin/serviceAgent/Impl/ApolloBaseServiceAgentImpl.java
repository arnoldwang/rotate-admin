package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.apollobase.api.DepartmentGroupService;
import com.dianping.rotate.admin.serviceAgent.ApolloBaseServiceAgent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by shenyoujun on 15/1/21.
 */
public class ApolloBaseServiceAgentImpl implements ApolloBaseServiceAgent {

    @Autowired
    private DepartmentGroupService departmentGroupService;

    public void test(){
        departmentGroupService.getGroup(1);
    }


}
