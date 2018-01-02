package com.alchemist.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Baowen on 2018/1/2.
 * 用于显示购物车中的每一个商品条目
 */
public class CartProductVo implements Serializable {

    private static final long serialVersionUID = -5261850276557030668L;

    private Integer id;
    private Integer userId;
    private Integer productId;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品子标题
     */
    private String productSubtitle;

    /**
     * 图片
     */
    private String productMainImage;

    /**
     * 商品单价
     */
    private BigDecimal productPrice;

    /**
     * 商品状态
     */
    private Integer productStatus;

    /**
     * 商品总价
     */
    private BigDecimal productTotalPrice;

    /**
     * 商品库存
     */
    private Integer productStock;

    /**
     * 是否选中
     */
    private Integer productChecked;

    /**
     * 限制数量的一个返回结果
     */
    private String limitQuantity;

    public CartProductVo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }
}
