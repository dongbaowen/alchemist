package com.alchemist.vo;

import com.alchemist.pojo.Product;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Created by Baowen on 2017/12/10.
 */
public class ProductDetailVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subTitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private String createTime;

    private String updateTime;

    private String imageHost;

    private Integer parentCategoryId;

    public ProductDetailVo() {
    }

    public ProductDetailVo(Product product, String imageHost) {
        this.id = product.getId();
        this.categoryId = product.getCategoryId();
        this.name = product.getName();
        this.subTitle = product.getSubtitle();
        this.mainImage = product.getMainImage();
        this.subImages = product.getSubImages();
        this.detail = product.getDetail();
        this.price = product.getPrice();
        this.status = product.getStatus();
        this.stock = product.getStock();
        this.createTime = new DateTime(product.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss");
        this.updateTime = new DateTime(product.getUpdateTime()).toString("yyyy-MM-dd HH:mm:ss");
        this.imageHost = imageHost;
        this.parentCategoryId = product.getCategoryId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
