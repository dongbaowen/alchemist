package com.alchemist.service.impl;

import com.alchemist.dao.ShippingMapper;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.Shipping;
import com.alchemist.service.IShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Baowen on 2018/1/13.
 */
@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Resource
    private ShippingMapper shippingMapper;

    public Integer add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            return rowCount;
        }
        throw new BusinessException(1, "新建地址失败");
    }

    public void del(Integer userId, Integer shippingId) {
        int resultCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if (!(resultCount > 0)) {
            throw new BusinessException(1, "删除地址失败");
        }
    }

    public Integer update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0) {
            return rowCount;
        }
        throw new BusinessException(1, "更新地址失败");
    }

    public Shipping select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if(shipping == null){
            throw new BusinessException(1, "无法查询到该地址");
        }
        return shipping;
    }


    public PageInfo list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return pageInfo;
    }
}
