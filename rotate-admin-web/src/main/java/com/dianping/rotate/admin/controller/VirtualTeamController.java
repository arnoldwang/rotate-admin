package com.dianping.rotate.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dev_wzhang on 2015/2/13.
 */
@Controller
@RequestMapping("/virtual-team")
public class VirtualTeamController {

    @RequestMapping("/query")
    public Object queryVirtualTeam(@RequestParam Integer bizId){
        return null;
    }

}
