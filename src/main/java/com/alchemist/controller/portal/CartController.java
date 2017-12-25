package com.alchemist.controller.portal;

import com.alchemist.common.Response;
import com.alchemist.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Baowen on 2017/12/21.
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private ICartService iCartService;

    @RequestMapping("/add")
    @ResponseBody
    public Response add() {
        return null;
    }

}
