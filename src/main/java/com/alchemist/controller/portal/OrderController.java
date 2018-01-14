package com.alchemist.controller.portal;

import com.alchemist.common.Response;
import com.alchemist.pojo.User;
import com.alchemist.service.IOrderService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Baowen on 2018/1/14.
 */
@Controller
@RequestMapping("/order/")
public class OrderController {

    @Resource
    private IOrderService iOrderService;

    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response create(Integer shippingId){
        User user = new User();
        return Response.createBySuccess(iOrderService.createOrder(user.getId(), shippingId));
    }

    @RequestMapping(
            value = "/cancel",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response cancel(Long orderNo){
        User user = new User();
        iOrderService.cancel(user.getId(), orderNo);
        return Response.createBySuccess();
    }

    @RequestMapping(
            value = "/getOrderCartProduct",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getOrderCartProduct(Long orderNo){
        User user = new User();
        return Response.createBySuccess(iOrderService.getOrderCartProduct(user.getId()));
    }


    @RequestMapping(
            value = "detail",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response detail(Long orderNo){
        User user = new User();
        return Response.createBySuccess(iOrderService.getOrderDetail(user.getId(),orderNo));
    }

    @RequestMapping(
            value = "list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = new User();
        return Response.createBySuccess(iOrderService.getOrderList(user.getId(),pageNum,pageSize));
    }
}
