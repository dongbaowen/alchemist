package com.alchemist.service;

import com.alchemist.vo.OrderItemVo;
import com.alchemist.vo.OrderVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Baowen on 2018/1/13.
 */
public interface IOrderService {

    OrderVo createOrder(Integer userId, Integer shippingId);

    void cancel(Integer userId,Long orderNo);

    List<OrderItemVo> getOrderCartProduct(Integer userId);

    OrderVo getOrderDetail(Integer userId, Long orderNo);

    PageInfo getOrderList(Integer userId, int pageNum, int pageSize);

    PageInfo manageList(int pageNum,int pageSize);

    OrderVo manageDetail(Long orderNo);

    PageInfo manageSearch(Long orderNo,int pageNum,int pageSize);

    void manageSendGoods(Long orderNo);
}
