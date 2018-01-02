package com.alchemist.controller.backend;

import com.alchemist.common.FileTypes;
import com.alchemist.common.Response;
import com.alchemist.pojo.Product;
import com.alchemist.service.IProductService;
import com.alchemist.util.OssService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Baowen on 2017/12/10.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

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


    @RequestMapping(
            value = "/detail",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response detail(
            @RequestParam("productId") Integer productId,
            HttpSession session) {

//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());
        return Response.createBySuccess(iProductService.manageProductDetail(productId));
    }

    @RequestMapping(
            value = "/list",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response list(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            HttpSession session) {

//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());
        return Response.createBySuccess(iProductService.getProductList(pageNum, pageSize));
    }


    @RequestMapping(
            value = "/search",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response search(
//            @RequestParam(value = "productName", required = false) String productName,
//            @RequestParam(value = "productId", required = false) Integer productId,
//            @RequestParam("pageNum") Integer pageNum,
//            @RequestParam("pageSize") Integer pageSize,
            @RequestBody Map<String, String> params,
            HttpSession session) {

//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());
        return Response.createBySuccess(iProductService.searchProductList(params.get("productName"), Integer.parseInt(params.get("productId")), 1, 1).getList());
    }


    @RequestMapping("upload")
    @ResponseBody
    public Response upload(
            HttpSession session,
            @RequestParam(value = "upload_file", required = false) MultipartFile file,
            HttpServletRequest request) {

//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录管理员");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {

        String fileName = file.getOriginalFilename();
        String path = OssService.uploadImage(FileTypes.PRODUCT_DETAIL_IMAGE, fileName, file);
        return Response.createBySuccess(path);

//        } else {
//            return ServerResponse.createByErrorMessage("无权限操作");
//        }
    }
}
