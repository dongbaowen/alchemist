package com.alchemist.service;

import com.alchemist.pojo.Shipping;
import com.github.pagehelper.PageInfo;

/**
 * Created by Baowen on 2018/1/13.
 */
public interface IShippingService {

    Integer add(Integer userId, Shipping shipping);

    void del(Integer userId, Integer shippingId);

    Integer update(Integer userId, Shipping shipping);

    Shipping select(Integer userId, Integer shippingId);

    PageInfo list(Integer userId, int pageNum, int pageSize);
}
