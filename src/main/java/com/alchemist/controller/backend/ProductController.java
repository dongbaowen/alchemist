package com.alchemist.controller.backend;

import com.alchemist.common.Response;
import com.alchemist.pojo.Product;
import com.alchemist.service.IProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Baowen on 2017/12/10.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductController {

    @Resource
    private IProductService iProductService;

    @RequestMapping(
            value = "/saveOrUpdate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response saveOrUpdateProduct(
            @RequestBody Product product,
            HttpSession session) {

//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        iProductService.saveOrUpdateProduct(product);
        return Response.createBySuccess(null);
    }


    @RequestMapping(
            value = "/updateStatus",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response saveOrUpdateProduct(
            @RequestParam("productId") Integer productId,
            @RequestParam("status") Integer status,
            HttpSession session) {

//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        iProductService.updateStatus(productId, status);
        return Response.createBySuccess(null);
    }
}
