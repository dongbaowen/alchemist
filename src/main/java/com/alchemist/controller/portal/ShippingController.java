package com.alchemist.controller.portal;

import com.alchemist.common.Response;
import com.alchemist.pojo.Shipping;
import com.alchemist.pojo.User;
import com.alchemist.service.IShippingService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Baowen on 2018/1/13.
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {

    @Resource
    private IShippingService iShippingService;

    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response add(HttpSession session, Shipping shipping) {
        User user = new User(); // TODO: 2018/1/13 校验用户是否存在
//        if (user == null) {
//            throw new BusinessException(1, "用户为空");
//        }
        iShippingService.add(user.getId(), shipping);
        return Response.createBySuccess();
    }

    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(HttpSession session, Integer shippingId) {
        User user = new User(); // TODO: 2018/1/13 校验用户是否存在
//        if (user == null) {
//            throw new BusinessException(1, "用户为空");
//        }
        iShippingService.del(user.getId(), shippingId);
        return Response.createBySuccess();
    }

    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response update(HttpSession session, Shipping shipping) {
        User user = new User(); // TODO: 2018/1/13 校验用户是否存在
//        if (user == null) {
//            throw new BusinessException(1, "用户为空");
//        }
        iShippingService.update(user.getId(), shipping);
        return Response.createBySuccess();
    }


    @RequestMapping(
            value = "/select",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response select(HttpSession session, Integer shippingId) {
        User user = new User(); // TODO: 2018/1/13 校验用户是否存在
//        if (user == null) {
//            throw new BusinessException(1, "用户为空");
//        }
        return Response.createBySuccess(iShippingService.select(user.getId(), shippingId));
    }

    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response list(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = new User(); // TODO: 2018/1/13 校验用户是否存在
//        if (user == null) {
//            throw new BusinessException(1, "用户为空");
//        }
        return Response.createBySuccess(iShippingService.list(user.getId(), pageNum, pageSize));
    }
}
