package com.dianping.rotate.admin.serviceAgent;

import com.dianping.rotate.admin.dto.UserShopInfoDTO;

import java.util.List;

/**
 * User: zhenwei.wang
 * Date: 15-3-5
 */
public interface RotateServiceAgent {

	List<UserShopInfoDTO> getUserShopInfo(int shopId, int bizId, int cityId, int pageSize, int pageIndex);

	int getTotalNumOfUserShop(int shopId, int bizId, int cityId);

	List<UserShopInfoDTO> changeOwner(int bizId, int cityId, int salesId, int rotateGroupShopId, int shopId,
									  int shopGroupId, int rotateGroupId, int pageSize, int pageIndex);
}
