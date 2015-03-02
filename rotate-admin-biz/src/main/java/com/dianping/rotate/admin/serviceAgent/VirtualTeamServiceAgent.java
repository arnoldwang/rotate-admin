package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.admin.vo.VirtualTeamVo;

import java.util.List;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
public interface VirtualTeamServiceAgent {

    /**
     * 查询虚拟组
     * @param bizId
     * @return
     */
    public List<VirtualTeamVo> queryVirtualTeamList(Integer bizId);

    public int saveVirtualTeam(VirtualTeamVo virtualTeamVo,int ooperatorId);

    public VirtualTeamVo getVirtualTeam(Integer teamId);

    Boolean deleteVirtualTeam(Integer teamId,int operatorId);
}
