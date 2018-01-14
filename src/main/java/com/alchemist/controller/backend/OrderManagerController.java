package com.alchemist.controller.backend;

import com.alchemist.common.Response;
import com.alchemist.service.IOrderService;
import com.alchemist.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Baowen on 2018/1/14.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManagerController {

    @Resource
    private IUserService iUserService;

    @Resource
    private IOrderService iOrderService;

    @RequestMapping("list")
    @ResponseBody
    public Response orderList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){

        // TODO: 2018/1/14 用户未登录,请登录管理员/校验权限问题
        return Response.createBySuccess(iOrderService.manageList(pageNum,pageSize));
    }

    @RequestMapping("detail")
    @ResponseBody
    public Response orderDetail(HttpSession session, Long orderNo){

        // TODO: 2018/1/14 用户未登录,请登录管理员/校验权限问题
        return Response.createBySuccess(iOrderService.manageDetail(orderNo));
    }



    @RequestMapping("search")
    @ResponseBody
    public Response orderSearch(@RequestParam("orderNo") Long orderNo,
                                @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        return Response.createBySuccess(iOrderService.manageSearch(orderNo,pageNum,pageSize));
    }



    @RequestMapping("sendGoods")
    @ResponseBody
    public Response orderSendGoods(Long orderNo){
        iOrderService.manageSendGoods(orderNo);
        return Response.createBySuccess();
    }

}
