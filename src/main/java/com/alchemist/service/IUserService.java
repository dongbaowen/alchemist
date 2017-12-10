package com.alchemist.service;

import com.alchemist.pojo.User;
import com.alchemist.pojo.dto.UserDTO;

/**
 * Created by Baowen on 2017/12/3.
 */
public interface IUserService {

    User login(String username, String password);

    void signUp(UserDTO userDTO);

    boolean checkValid(String str, String type);

    boolean isAdminPermission(String username);
}
