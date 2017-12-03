package com.alchemist.controller.backend;

import com.alchemist.common.Const;
import com.alchemist.common.RedisKey;
import com.alchemist.common.Response;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.User;
import com.alchemist.pojo.dto.UserVO;
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
@RequestMapping("/manage/user")
public class UserManagerController {

    @Resource
    private IUserService iUserService;

    @RequestMapping(
            value = "/singIn",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response singIn(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           HttpSession session) {

        User user = iUserService.login(username, password);
        if (Objects.equals(Const.Role.ROLE_CUSTOMER, user.getRole())) {
            throw new BusinessException(1, "管理员权限错误");
        }

        UserVO userVO = new UserVO(user);
        session.setAttribute(String.format(RedisKey.LOGIN_USER.getKey(), username), userVO);
        return Response.createBySuccess(userVO);
    }
}
