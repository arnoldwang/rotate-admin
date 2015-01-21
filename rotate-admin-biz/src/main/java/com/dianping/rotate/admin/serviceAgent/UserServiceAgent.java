package com.dianping.rotate.admin.serviceAgent;

import com.dianping.ba.base.organizationalstructure.api.user.dto.UserDto;

import java.util.List;

public interface UserServiceAgent {

    List<UserDto> searchUser(String userName);

}
