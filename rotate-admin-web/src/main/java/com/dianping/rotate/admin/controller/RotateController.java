package com.dianping.rotate.admin.controller;

import com.dianping.rotate.admin.dto.UserShopInfoDTO;
import com.dianping.rotate.admin.model.ShopModel;
import com.dianping.rotate.admin.serviceAgent.RotateServiceAgent;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * User: zhenwei.wang
 * Date: 15-3-4
 */
@Controller
@RequestMapping("/rotate")
public class RotateController {

	@Autowired
	RotateServiceAgent rotateServiceAgent;

	@RequestMapping(value = "/queryShopInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryShopInfo(@RequestBody ShopModel shopModel){
		Map<String, Object> result = Maps.newHashMap();
		List<UserShopInfoDTO> userShopInfoDTOs = rotateServiceAgent.getUserShopInfo(shopModel.getShopID(),
				shopModel.getBizID(), shopModel.getCityID(), shopModel.getPageSize(), shopModel.getPageIndex());
		int total = rotateServiceAgent.getTotalNumOfUserShop(shopModel.getShopID(),shopModel.getBizID(),
				shopModel.getCityID());
		result.put("items", userShopInfoDTOs);
		result.put("total", total);
		return result;
	}
}
