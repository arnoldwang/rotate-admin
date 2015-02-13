package com.dianping.rotate.admin.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
@Data
public class TeamMemberVo implements Serializable {
    private Integer memberId;
    private String memberName;
}
