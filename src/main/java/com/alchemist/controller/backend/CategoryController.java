package com.alchemist.controller.backend;

import com.alchemist.common.Const;
import com.alchemist.common.RedisKey;
import com.alchemist.common.Response;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.User;
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
import java.util.Objects;

/**
 * Created by Baowen on 2017/12/3.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryController {

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

        //校验是否登录
        UserVO loginUser = (UserVO) session.getAttribute(RedisKey.LOGIN_USER.getKey());

        //校验登录用户权限

        //添加目录
        iCategoryService.addCategory(parentId, categroyName, loginUser.getUsername());

        return null;
    }

}
