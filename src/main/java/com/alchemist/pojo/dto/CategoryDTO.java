package com.alchemist.pojo.dto;

import java.io.Serializable;

/**
 * Created by Baowen on 2017/12/3.
 */
public class CategoryDTO implements Serializable {

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer parentId, String name, Boolean status) {
        this.parentId = parentId;
        this.name = name;
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
