package com.dianping.rotate.admin.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
@Data
public class VirtualTeamVo implements Serializable {

    private Integer id;
    private Integer createBy;
    private Integer updateBy;
    private Date createTime;
    private Date updateTime;
    private String teamName;
    private Integer parentTeamId;
    private String parentTeamName;
    private Integer status;
    private Integer teamLeaderId;
    private String teamLeaderName;
    private Integer territoryId;
    private String territoryName;
    private Integer bizId;
    private Integer teamType;
    private List<TeamMemberVo> teamMembers;
}

