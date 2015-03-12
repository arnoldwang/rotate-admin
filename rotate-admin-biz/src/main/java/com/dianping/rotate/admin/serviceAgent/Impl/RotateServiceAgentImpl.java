package com.dianping.rotate.admin.serviceAgent.Impl;

import com.dianping.ba.base.organizationalstructure.api.user.UserService;
import com.dianping.combiz.service.CityService;
import com.dianping.rotate.admin.dto.UserShopInfoDTO;
import com.dianping.rotate.admin.exceptions.ApplicationException;
import com.dianping.rotate.admin.serviceAgent.RotateServiceAgent;
import com.dianping.rotate.admin.utils.JsonUtils;
import com.dianping.rotate.core.api.dto.RotateGroupUserDTO;
import com.dianping.rotate.core.api.service.RotateGroupUserService;
import com.dianping.rotate.shop.api.ApolloShopService;
import com.dianping.rotate.shop.api.RotateGroupService;
import com.dianping.rotate.shop.api.RotateGroupShopService;
import com.dianping.rotate.shop.constants.ApolloShopStatusEnum;
import com.dianping.rotate.shop.constants.ApolloShopTypeEnum;
import com.dianping.rotate.shop.constants.RotateGroupTypeEnum;
import com.dianping.rotate.shop.dto.ApolloShopDTO;
import com.dianping.rotate.shop.dto.RotateGroupShopDTO;
import com.dianping.shopremote.remote.ShopService;
import com.dianping.shopremote.remote.dto.ShopDTO;
import com.dianping.swallow.common.message.Destination;
import com.dianping.swallow.producer.Producer;
import com.dianping.swallow.producer.ProducerConfig;
import com.dianping.swallow.producer.ProducerMode;
import com.dianping.swallow.producer.impl.ProducerFactoryImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
	RotateGroupService rotateGroupService;

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

		ApolloShopDTO shop = apolloShopService.getApolloShop(shopId, bizId);
		if (shop == null)
			throw new ApplicationException("ShopID不存在，请确认后重新输入!");

		int offset = (pageIndex - 1) * pageSize;
		List<RotateGroupShopDTO> rotateGroupShopDTOs = rotateGroupShopService.getRotateGroupShopByShopGroupIDAndBizIDAndCityID(
				shop.getShopGroupID(), bizId, cityId, pageSize, offset);

		for (RotateGroupShopDTO rotateGroupShopDTO : rotateGroupShopDTOs) {
			UserShopInfoDTO userShopInfoDTO = new UserShopInfoDTO();
			ApolloShopDTO apolloShopDTO = apolloShopService.getApolloShop(rotateGroupShopDTO.getShopID(), bizId);
			ShopDTO shopDTO = shopService.loadShop(rotateGroupShopDTO.getShopID());
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
			userShopInfoDTO.setRotateGroupShopId(rotateGroupShopDTO.getId());
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

	@Override
	public List<UserShopInfoDTO> changeOwner(int bizId, int cityId, int salesId, int rotateGroupShopId, int shopId,
											 int shopGroupId, int rotateGroupId, int pageSize, int pageIndex) {
		List<RotateGroupShopDTO> rotateGroupShopDTOs =
				rotateGroupShopService.getAllRotateGroupShopByShopGroupIDAndBizIDAndCityID(shopGroupId, bizId, cityId);
		int newRotateGroupId = 0;
		String messageType = null;

		for (RotateGroupShopDTO rotateGroupShopDTO : rotateGroupShopDTOs) {
			RotateGroupUserDTO rotateGroupUserDTO = rotateGroupUserService.findByRotateGroupId(rotateGroupShopDTO.getRotateGroupID());
			if (rotateGroupUserDTO != null && salesId == rotateGroupUserDTO.getUserId()){
				if (!rotateGroupUserService.validateNotOnlineGroupCountLimit(RotateGroupTypeEnum.CHAIN.getCode(), salesId).isSuccess())
					throw new ApplicationException("销售私海连锁店数量已满，请确认后重试！");

				newRotateGroupId = rotateGroupUserDTO.getRotateGroupId();
				rotateGroupService.updateType(newRotateGroupId, 1);//合并到已有的轮转组，则已有的轮转组变为连锁店
				messageType = "merge";
				break;
			}
		}
		if (0 == newRotateGroupId) {
			if (!rotateGroupUserService.validateNotOnlineGroupCountLimit(RotateGroupTypeEnum.SINGLE.getCode(), salesId).isSuccess())
				throw new ApplicationException("销售私海单店数量已满，请确认后重试！");
			newRotateGroupId = rotateGroupService.createRotateGroup(bizId).getId();
			messageType = "split";
		}

		updateRotateGroupShop(rotateGroupShopId, newRotateGroupId, shopId, shopGroupId);
		sendMessage(rotateGroupId, newRotateGroupId, salesId, messageType);

		return getUserShopInfo(shopId, bizId, cityId, pageSize, pageIndex);
	}

	@SuppressWarnings("unchecked")
	private void sendMessage(int rotateGroupId, int newRotateGroupId, int newUserId, String messageType) {
		try {
			RotateGroupUserDTO oldUser = rotateGroupUserService.findByRotateGroupId(rotateGroupId);
			Integer oldUserId = null;
			if (oldUser != null)
				oldUserId = oldUser.getUserId();

			Map<String, Object> messageBody = Maps.newHashMap();
			messageBody.put("type", messageType);
			Map<String, Integer> oldValue = Maps.newHashMap();
			oldValue.put("rotateGroupId", rotateGroupId);
			oldValue.put("owner", oldUserId);
			messageBody.put("oldValues", Lists.newArrayList(oldValue));
			Map<String, Integer> newValue = Maps.newHashMap();
			newValue.put("rotateGroupId", newRotateGroupId);
			newValue.put("owner", newUserId);
			messageBody.put("newValues", Lists.newArrayList(newValue));

			ProducerConfig config = new ProducerConfig();
			config.setMode(ProducerMode.SYNC_MODE);
			Producer p = ProducerFactoryImpl.getInstance().createProducer(Destination.topic("dp_apollo_rotategroup_change"), config);
			p.sendMessage(JsonUtils.toStr(messageBody));
		} catch (Exception e) {
			throw new ApplicationException("轮转组合/拆分并异常", e.getMessage());
		}
	}


	private void updateRotateGroupShop(int rotateGroupShopId, int newRotateGroupId, int shopId, int shopGroupId) {
		RotateGroupShopDTO newRotateGroupShop = new RotateGroupShopDTO();
		newRotateGroupShop.setId(rotateGroupShopId);
		newRotateGroupShop.setRotateGroupID(newRotateGroupId);
		newRotateGroupShop.setShopID(shopId);
		newRotateGroupShop.setShopGroupID(shopGroupId);
		newRotateGroupShop.setStatus(1);
		rotateGroupShopService.updateRotateGroupShop(newRotateGroupShop);
	}

}
