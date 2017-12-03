package com.alchemist.controller.portal;

import com.alchemist.common.RedisKey;
import com.alchemist.common.Response;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.User;
import com.alchemist.pojo.dto.UserDTO;
import com.alchemist.pojo.dto.UserVO;
import com.alchemist.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by Baowen on 2017/12/3.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService iUserService;

    @RequestMapping(
            value = "/singIn",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String singIn(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {

        UserVO userVO = new UserVO(iUserService.login(username, password));
        session.setAttribute(RedisKey.LOGIN_USER.getKey(), userVO);
        return Response.createBySuccess(userVO).toString();
    }

    @RequestMapping(
            value = "/singUp",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String singUp(@RequestBody UserDTO userDTO){
        iUserService.signUp(userDTO);
        return null;
    }


    @RequestMapping(
            value = "/checkValid",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String checkValid(@RequestParam("str") String str, @RequestParam("type") String type){
        iUserService.checkValid(str, type);
        throw new BusinessException(1, "234");
    }

}
