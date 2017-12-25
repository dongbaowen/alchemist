package com.alchemist.controller.portal;

import com.alchemist.common.Response;
import com.alchemist.service.IProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Baowen on 2017/12/20.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService productService;

    @RequestMapping(
            value = "/detail",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getProductDetail(@RequestParam("productId") Integer productId) {
        return Response.createBySuccess(productService.getProductDetail(productId));
    }

    @RequestMapping(
            value = "/getList",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getProductList(
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam(value = "keyWords", required = false) String keyWords,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "orderBy", required = false) String orderBy) {
        return Response.createBySuccess(productService.getProductList(keyWords, categoryId, pageNum, pageSize, orderBy));
    }
}
