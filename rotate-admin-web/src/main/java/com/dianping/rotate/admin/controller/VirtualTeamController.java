package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.serviceAgent.VirtualTeamServiceAgent;
import com.dianping.rotate.admin.util.LoginUtils;
import com.dianping.rotate.admin.vo.VirtualTeamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
@Controller
@RequestMapping("/virtual-team")
public class VirtualTeamController {

    @Autowired
    VirtualTeamServiceAgent virtualTeamServiceAgent;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public List<VirtualTeamVo> queryVirtualTeam(@RequestParam Integer bizId) {
        return virtualTeamServiceAgent.queryVirtualTeamList(bizId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveVirtualTeam(@RequestBody VirtualTeamVo virtualTeamVo) {
        virtualTeamServiceAgent.saveVirtualTeam(virtualTeamVo, LoginUtils.getUserLoginId());

        return "保存成功";
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public String deleteVirtualTeam(@RequestParam(value = "teamId") Integer teamId) {
        virtualTeamServiceAgent.deleteVirtualTeam(teamId);

        return "删除成功";
    }
}
