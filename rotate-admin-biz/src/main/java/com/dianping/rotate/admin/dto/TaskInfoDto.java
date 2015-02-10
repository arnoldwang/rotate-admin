package com.dianping.rotate.admin.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by shenyoujun on 15/2/9.
 */
public class TaskInfoDto implements Serializable{
    private Integer id;
    private String content;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
