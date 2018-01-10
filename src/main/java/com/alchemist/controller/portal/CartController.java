package com.alchemist.controller.portal;

import com.alchemist.common.Const;
import com.alchemist.common.Response;
import com.alchemist.service.ICartService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Baowen on 2017/12/21.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private ICartService iCartService;

    /**
     * 添加购物项
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/add",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response add(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.add(params.getInteger("userId"),
                        params.getInteger("productId"),
                        params.getInteger("count")));
    }

    /**
     * 更新购物项
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response update(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.update(params.getInteger("userId"),
                        params.getInteger("productId"),
                        params.getInteger("count")));
    }

    /**
     * 删除购物项
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response delete(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.delete(params.getInteger("userId"),
                        params.getString("productIdsStr")));
    }


    /**
     * 获取用户购物项列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response list(@RequestParam("userId") Integer userId) {
        return Response.createBySuccess(iCartService.list(userId));
    }


    /**
     * 全选
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/selectAll",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response selectAll(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.changeChecked(params.getInteger("userId"),
                        null,
                        Const.Cart.CHECKED));
    }

    /**
     * 全反选
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/unSelectAll",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unSelectAll(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.changeChecked(params.getInteger("userId"),
                        null,
                        Const.Cart.UN_CHECKED));
    }


    /**
     * 选择某一项
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/selectProduct",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response selectProduct(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.changeChecked(params.getInteger("userId"),
                        params.getInteger("productId"),
                        Const.Cart.CHECKED));
    }


    /**
     * 反选某一项
     *
     * @param params
     * @return
     */
    @RequestMapping(
            value = "/unSelectProduct",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response unSelectProduct(@RequestBody JSONObject params) {
        return Response.createBySuccess(
                iCartService.changeChecked(params.getInteger("userId"),
                        params.getInteger("productId"),
                        Const.Cart.UN_CHECKED));
    }


    /**
     * 反选某一项
     *
     * @param userId
     * @return
     */
    @RequestMapping(
            value = "/getCartNum",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getCartNum(@RequestParam("userId") Integer userId) {
        return Response.createBySuccess(iCartService.getQuantityOfCart(userId));
    }

}
