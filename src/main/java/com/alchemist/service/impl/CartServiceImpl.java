package com.alchemist.service.impl;

import com.alchemist.common.Const;
import com.alchemist.dao.CartMapper;
import com.alchemist.dao.ProductMapper;
import com.alchemist.pojo.Cart;
import com.alchemist.pojo.Product;
import com.alchemist.service.ICartService;
import com.alchemist.util.BigDecimalUtil;
import com.alchemist.vo.CartProductVo;
import com.alchemist.vo.CartVo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Baowen on 2017/12/21.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Transactional
    public CartVo add(Integer userId, Integer productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(productId);
            cart.setChecked(Const.Cart.CHECKED);
            cart.setQuantity(count);
            cartMapper.insert(cart);
        } else {
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return getCartVoLimit(userId);
    }

    @Transactional
    public CartVo update(Integer userId, Integer productId, Integer count) {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return getCartVoLimit(userId);
    }

    @Transactional
    public CartVo delete(Integer userId, String productIdsStr) {
        List<String> productIds = Splitter.on(",").trimResults().splitToList(productIdsStr);
        if (CollectionUtils.isNotEmpty(productIds)) {
            cartMapper.deleteCartByProductIds(userId, productIds);
        }
        return getCartVoLimit(userId);
    }

    public CartVo list(Integer userId) {
        return getCartVoLimit(userId);
    }

    public CartVo changeChecked(Integer userId, Integer productId, Integer checked) {
        cartMapper.changeCheckedByUserIdAndProductId(userId, productId, checked);
        return getCartVoLimit(userId);
    }

    public int getQuantityOfCart(Integer userId) {
        return cartMapper.selectCartProductCount(userId);
    }

    /**
     * 获取用户的购物车
     *
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        BigDecimal cartTotalPrice = BigDecimal.ZERO;

        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cart : cartList) {
                /**封装CartProductVo*/
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null) {
                    CartProductVo cartProductVo = new CartProductVo();
                    cartProductVo.setId(cart.getId());
                    cartProductVo.setUserId(cart.getUserId());
                    cartProductVo.setProductId(cart.getProductId());
                    cartProductVo.setProductChecked(cart.getChecked());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductStock(product.getStock());

                    /**封装CartProductVo数量*/
                    int quantity;
                    if (product.getStock() >= cart.getQuantity()) {
                        //够
                        quantity = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        //超出库存
                        quantity = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //更新Cart表中该记录的库存状态
                        cart.setQuantity(quantity);
                        cartMapper.updateByPrimaryKeySelective(cart);
                    }
                    cartProductVo.setQuantity(quantity);

                    /**封装CartProductVo总价*/
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(cartProductVo.getProductPrice().doubleValue(), cartProductVo.getQuantity()));

                    if (cartProductVo.getProductChecked() == Const.Cart.CHECKED) {
                        cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                    }

                    /**添加到list*/
                    cartProductVoList.add(cartProductVo);
                }
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setAllChecked(isAllChecked(userId));
        return cartVo;
    }

    private boolean isAllChecked(Integer userId) {
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }

}
