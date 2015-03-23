package com.dianping.rotate.admin.vo;

import java.util.List;

/**
 * Created by shenyoujun on 15/3/23.
 */
public class SearchShopVo {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer bizId;
    private List<Integer> bus;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getBizId() {
        return bizId;
    }

    public void setBizId(Integer bizId) {
        this.bizId = bizId;
    }

    public List<Integer> getBus() {
        return bus;
    }

    public void setBus(List<Integer> bus) {
        this.bus = bus;
    }
}
