package com.alchemist.service.impl;

import com.alchemist.common.Const;
import com.alchemist.dao.CartMapper;
import com.alchemist.pojo.Cart;
import com.alchemist.service.ICartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Baowen on 2017/12/21.
 */
@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Resource
    private CartMapper cartMapper;

    public List add(Integer userId, Integer productId, Integer count) {
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

        return null;
    }

}
