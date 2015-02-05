package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.UserServiceAgent;
import com.dianping.rotate.admin.utils.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by shenyoujun on 15/1/21.
 */
@Component
public class UserServiceAgentImpl implements UserServiceAgent{

    @Autowired
    private UserService userService;

    @Override
    public List<UserDto> searchUser(String userName) {
        try{
            List<UserDto> userDtoList = userService.queryUserByKeyword(userName, ConfigUtils.isWantResignation());
            return userDtoList;
        }catch (Exception e){
            throw new ApplicationException("获取用户信息失败");
        }
    }
}
