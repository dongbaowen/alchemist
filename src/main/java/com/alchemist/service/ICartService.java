package com.alchemist.service;

import com.alchemist.vo.CartVo;

/**
 * Created by Baowen on 2017/12/21.
 */
public interface ICartService {

    CartVo add(Integer userId, Integer productId, Integer count);

    CartVo update(Integer userId, Integer productId, Integer count);

    CartVo delete(Integer userId, String productIdsStr);

    CartVo list(Integer userId);

    CartVo changeChecked(Integer userId, Integer productId, Integer checked);

    int getQuantityOfCart(Integer userId);
}
