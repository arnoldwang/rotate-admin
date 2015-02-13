package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.admin.vo.VirtualTeamVo;
import com.dianping.rotate.org.dto.TigerTeamDto;

import java.util.List;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
public interface VirtualTeamServiceAgent {

    public List<VirtualTeamVo> queryTigerTeamList(Integer bizId);
}
