package com.alchemist.controller.backend;

import com.alchemist.common.RedisKey;
import com.alchemist.common.Response;
import com.alchemist.pojo.Category;
import com.alchemist.pojo.dto.UserVO;
import com.alchemist.service.ICategoryService;
import com.alchemist.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Baowen on 2017/12/3.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Resource
    private IUserService iUserService;

    @Resource
    private ICategoryService iCategoryService;

    @RequestMapping(
            value = "/singIn",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response addCategory(
            @RequestParam("parentId") int parentId,
            @RequestParam("categroyName") String categroyName,
            HttpSession session) {

        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        //添加目录
        iCategoryService.addCategory(parentId, categroyName, loginUser.getUsername());
        return Response.createBySuccess(null);
    }


    @RequestMapping(
            value = "/getCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getCategory(
            @RequestParam(value = "parentId", defaultValue = "0") int parentId,
            HttpSession session) {

        //校验是否登录
//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        //添加目录
        List<Category> categoryList = iCategoryService.getCategoryByParentId(parentId, "");
        return Response.createBySuccess(categoryList);
    }


    @RequestMapping(
            value = "/getDeepCategories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response getDeepCategories(
            @RequestParam(value = "parentId", defaultValue = "0") int parentId,
            HttpSession session) {

        //校验是否登录
//        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        //添加目录
        List<Integer> categoryIdList = iCategoryService.getDeepCategoryByParentId(parentId, "");
        return Response.createBySuccess(categoryIdList);
    }
}
