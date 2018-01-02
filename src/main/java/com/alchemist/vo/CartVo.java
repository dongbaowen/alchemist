package com.alchemist.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Baowen on 2018/1/2.
 * 用于显示整个购物车
 */
public class CartVo implements Serializable {

    private static final long serialVersionUID = 3081332111329997204L;

    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;

    public CartVo() {
    }

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }
}
