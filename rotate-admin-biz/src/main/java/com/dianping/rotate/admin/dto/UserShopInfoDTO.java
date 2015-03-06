package com.dianping.rotate.admin.dto;

/**
 * User: zhenwei.wang
 * Date: 15-3-5
 */
public class UserShopInfoDTO {

	private String cityName;

	private String shopName;

	private String type;

	private String shopStatus;

	private int shopGroupId;

	private int rotateGroupId;

	private int shopId;

	private String owner;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	public int getShopGroupId() {
		return shopGroupId;
	}

	public void setShopGroupId(int shopGroupId) {
		this.shopGroupId = shopGroupId;
	}

	public int getRotateGroupId() {
		return rotateGroupId;
	}

	public void setRotateGroupId(int rotateGroupId) {
		this.rotateGroupId = rotateGroupId;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
