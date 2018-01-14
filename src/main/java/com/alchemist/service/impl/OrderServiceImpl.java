package com.alchemist.service.impl;

import com.alchemist.common.Const;
import com.alchemist.dao.*;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.*;
import com.alchemist.service.IOrderService;
import com.alchemist.util.BigDecimalUtil;
import com.alchemist.vo.OrderItemVo;
import com.alchemist.vo.OrderProductVo;
import com.alchemist.vo.OrderVo;
import com.alchemist.vo.ShippingVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Baowen on 2018/1/13.
 */
@Service("iOrderService")
public class OrderServiceImpl implements IOrderService{

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ShippingMapper shippingMapper;

    @Transactional
    @Override
    public OrderVo createOrder(Integer userId, Integer shippingId) {

        Long orderNo = createOrderNo();
        List<Cart> carts = cartMapper.selectCheckedCartByUserId(userId);
        List<OrderItem> orderItemList = getOrderItemList(userId, carts, orderNo);

        //生成订单
        Order order = assembleOrder(userId, shippingId, getTotalPrice(orderItemList));

        //批量插入购物项
        orderItemMapper.batchInsert(orderItemList);

        //减少库存
        reduceStock(orderItemList);

        //清空购物车
        cleanCart(carts);

        //返回信息给前端
        return assembleOrderVo(order, orderItemList);
    }

    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();

        //封装订单信息
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setPaymentTime(new DateTime(order.getPaymentTime()).toString("yyyy-MM-dd HH:mm:ss"));
        orderVo.setSendTime(new DateTime(order.getSendTime()).toString("yyyy-MM-dd HH:mm:ss"));
        orderVo.setEndTime(new DateTime(order.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"));
        orderVo.setCreateTime(new DateTime(order.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        orderVo.setCloseTime(new DateTime(order.getCloseTime()).toString("yyyy-MM-dd HH:mm:ss"));

        //封装shippingVO
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }

        //封装orderItemVo
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        for(OrderItem orderItem : orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(new DateTime(orderItem.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        return orderItemVo;
    }

    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        return shippingVo;
    }

    private void reduceStock(List<OrderItem> orderItemList){
        for(OrderItem orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            productMapper.updateByPrimaryKey(product);
        }
    }

    private void cleanCart(List<Cart> carts){
        for(Cart cart : carts){
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment) {
        Order order = new Order();
        order.setOrderNo(createOrderNo());
        order.setStatus(Const.PaymentTypeEnum.ONLINE_PAY.getCode());
        order.setUserId(userId);
        order.setPayment(payment);
        order.setPostage(0);
        order.setShippingId(shippingId);

        //发货时间等等
        //付款时间等等
        int rowCount = orderMapper.insert(order);
        if (rowCount > 0) {
            return order;
        }
        return null;
    }

    private BigDecimal getTotalPrice(List<OrderItem> orderItemList) {
        BigDecimal payment = new BigDecimal("0");
        for (OrderItem orderItem : orderItemList) {
            BigDecimalUtil.mul(payment.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private List<OrderItem> getOrderItemList(Integer userId, List<Cart> carts, Long orderNo) {
        List<OrderItem> orderItemList = Lists.newArrayList();

        if (CollectionUtils.isEmpty(carts)) {
            throw new BusinessException(1, "购物车为空");
        }
        for (Cart cartItem : carts) {
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());

            if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
                throw new BusinessException(1, product.getName() + "不在售卖状态");
            }
            if (cartItem.getQuantity() > product.getStock()) {
                throw new BusinessException(1, product.getName() + "超出库存");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setUserId(userId);
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductName(product.getName());
            orderItem.setOrderNo(orderNo);
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartItem.getQuantity()));

            orderItemList.add(orderItem);
        }

        return orderItemList;
    }

    private Long createOrderNo() {
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }


    public void cancel(Integer userId,Long orderNo){
        Order order  = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            throw new BusinessException(1, "该用户此订单不存在");
        }
        if(order.getStatus() != Const.OrderStatusEnum.NO_PAY.getCode()){
            throw new BusinessException(1, "已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(Const.OrderStatusEnum.CANCELED.getCode());

        // TODO: 2018/1/14 这里是否需要恢复商品库存

        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(!(row > 0)){
            throw new BusinessException(1, "取消订单失败");
        }
    }


    public List<OrderItemVo> getOrderCartProduct(Integer userId){
        OrderProductVo orderProductVo = new OrderProductVo();
        //从购物车中获取数据

        List<Cart> cartList = cartMapper.selectCheckedCartByUserId(userId);
        List<OrderItem> orderItemList = getOrderItemList(userId, cartList, null);
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);
        orderProductVo.setOrderItemVoList(orderItemVoList);
        return orderItemVoList;
    }

    public OrderVo getOrderDetail(Integer userId, Long orderNo){
        Order order = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo,userId);
            return assembleOrderVo(order,orderItemList);
        }
        throw new BusinessException(1, "没有找到该订单");
    }


    public PageInfo getOrderList(Integer userId,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderList,userId);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return pageResult;
    }


    private List<OrderVo> assembleOrderVoList(List<Order> orderList,Integer userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(Order order : orderList){
            List<OrderItem>  orderItemList;
            if(userId == null){
                //todo 管理员查询的时候 不需要传userId
                orderItemList = orderItemMapper.getByOrderNo(order.getOrderNo());
            }else{
                orderItemList = orderItemMapper.getByOrderNoUserId(order.getOrderNo(),userId);
            }
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }





    //backend
    public PageInfo manageList(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.selectAllOrder();
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderList,null);
        PageInfo pageResult = new PageInfo(orderList);
        pageResult.setList(orderVoList);
        return pageResult;
    }


    public OrderVo manageDetail(Long orderNo){
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            return assembleOrderVo(order,orderItemList);
        }
        throw new BusinessException(1, "订单不存在");
    }


    public PageInfo manageSearch(Long orderNo,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            PageInfo pageResult = new PageInfo(Lists.newArrayList(order));
            pageResult.setList(Lists.newArrayList(orderVo));
            return pageResult;
        }
        throw new BusinessException(1, "订单不存在");
    }


    public void manageSendGoods(Long orderNo){
        Order order= orderMapper.selectByOrderNo(orderNo);
        if(order != null){
            if(order.getStatus() == Const.OrderStatusEnum.PAID.getCode()){
                order.setStatus(Const.OrderStatusEnum.SHIPPED.getCode());
                order.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
        throw new BusinessException(1, "订单不存在");
    }
}
