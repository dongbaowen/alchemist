package com.alchemist.common;

/**
 * Created by Baowen on 2017/12/27.
 */
public enum FileTypes {

    PRODUCT_DETAIL_IMAGE("1", "productDetail", "alchemist/product/detail/%s");

    private String typeCode;
    private String type;
    private String path;

    FileTypes(String typeCode, String type, String path) {
        this.typeCode = typeCode;
        this.type = type;
        this.path = path;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
