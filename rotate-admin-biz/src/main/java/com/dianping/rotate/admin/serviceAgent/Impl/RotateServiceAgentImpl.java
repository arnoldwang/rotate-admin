package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.combiz.service.CityService;
import com.dianping.rotate.admin.dto.UserShopInfoDTO;
import com.dianping.rotate.admin.serviceAgent.RotateServiceAgent;
import com.dianping.rotate.core.api.dto.RotateGroupUserDTO;
import com.dianping.rotate.core.api.service.RotateGroupUserService;
import com.dianping.rotate.shop.api.ApolloShopService;
import com.dianping.rotate.shop.api.RotateGroupShopService;
import com.dianping.rotate.shop.constants.ApolloShopStatusEnum;
import com.dianping.rotate.shop.constants.ApolloShopTypeEnum;
import com.dianping.rotate.shop.dto.ApolloShopDTO;
import com.dianping.rotate.shop.dto.RotateGroupShopDTO;
import com.dianping.shopremote.remote.ShopService;
import com.dianping.shopremote.remote.dto.ShopDTO;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: zhenwei.wang
 * Date: 15-3-5
 */
@Component
public class RotateServiceAgentImpl implements RotateServiceAgent {
	@Autowired
	ApolloShopService apolloShopService;

	@Autowired
	RotateGroupShopService rotateGroupShopService;

	@Autowired
	CityService cityService;

	@Autowired
	ShopService shopService;

	@Autowired
	RotateGroupUserService rotateGroupUserService;

	@Autowired
	UserService userService;

	@Override
	public List<UserShopInfoDTO> getUserShopInfo(int shopId, int bizId, int cityId, int pageSize, int pageIndex) {
		List<UserShopInfoDTO> userShopInfoDTOs = Lists.newArrayList();

		int offset = (pageIndex - 1) * pageSize;
		List<RotateGroupShopDTO> rotateGroupShopDTOs = rotateGroupShopService.getRotateGroupShopByShopGroupIDAndBizIDAndCityID(
				apolloShopService.getApolloShop(shopId, bizId).getShopGroupID(), bizId, cityId, pageSize, offset);

		for (RotateGroupShopDTO rotateGroupShopDTO : rotateGroupShopDTOs) {
			UserShopInfoDTO userShopInfoDTO = new UserShopInfoDTO();
			ApolloShopDTO apolloShopDTO = apolloShopService.getApolloShop(rotateGroupShopDTO.getShopID(), bizId);
			ShopDTO shopDTO = shopService.loadShop(rotateGroupShopDTO.getShopID());
			if (shopDTO.getCityId() != cityId)
				continue;
			//todo
			String userName = "公海";
			RotateGroupUserDTO rotateGroupUserDTO = rotateGroupUserService.findByRotateGroupId(rotateGroupShopDTO.getRotateGroupID());
			if (rotateGroupUserDTO != null) {
				int userId = rotateGroupUserDTO.getUserId();
				userName = userService.queryUserByLoginID(userId).getRealName();
			}

			userShopInfoDTO.setOwner(userName);
			userShopInfoDTO.setCityName(cityService.loadCity(cityId).getCityName());
			userShopInfoDTO.setShopName(shopDTO.getShopName());
			userShopInfoDTO.setType(ApolloShopTypeEnum.getDescByCode(apolloShopDTO.getType()));
			userShopInfoDTO.setShopStatus(ApolloShopStatusEnum.getDescByCode(apolloShopDTO.getShopStatus()));
			userShopInfoDTO.setShopGroupId(rotateGroupShopDTO.getShopGroupID());
			userShopInfoDTO.setRotateGroupId(rotateGroupShopDTO.getRotateGroupID());
			userShopInfoDTO.setShopId(rotateGroupShopDTO.getShopID());

			userShopInfoDTOs.add(userShopInfoDTO);
		}
		return userShopInfoDTOs;
	}

	@Override
	public int getTotalNumOfUserShop(int shopId, int bizId, int cityId) {
		int totalNum = rotateGroupShopService.getTotalNumByShopGroupIDAndBizIDAndCityID(
				apolloShopService.getApolloShop(shopId, bizId).getShopGroupID(), bizId, cityId);

		return totalNum;
	}
}
