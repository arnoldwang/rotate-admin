package com.dianping.rotate.admin.model;

/**
 * User: zhenwei.wang
 * Date: 15-3-4
 */
public class ShopModel {
	private int bizID;
	private int shopID;
	private int cityID;
	private int pageSize;
	private int pageIndex;

	public int getBizID() {
		return bizID;
	}

	public void setBizID(int bizID) {
		this.bizID = bizID;
	}

	public int getShopID() {
		return shopID;
	}

	public void setShopID(int shopID) {
		this.shopID = shopID;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
