package com.alchemist.dao;

import com.alchemist.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    Cart selectCartByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectCartByUserId(@Param("userId") Integer userId);

    int selectCartProductCheckedStatusByUserId(@Param("userId") Integer userId);

    int deleteCartByProductIds(@Param("userId") Integer userId, @Param("productIds") List<String> productIds);

    int changeCheckedByUserIdAndProductId(@Param("userId") Integer userId, @Param("productId") Integer productId, @Param("checked") Integer checked);

    int selectCartProductCount(@Param("userId") Integer userId);

    List<Cart> selectCheckedCartByUserId(@Param("userId") Integer userId);
}