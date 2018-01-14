package com.alchemist.service.impl;

import com.alchemist.common.Const;
import com.alchemist.common.ResponseCode;
import com.alchemist.dao.UserMapper;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.User;
import com.alchemist.pojo.dto.UserDTO;
import com.alchemist.service.IUserService;
import com.alchemist.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created by Baowen on 2017/12/3.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {

        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(1, "未找到该用户");
        }
        if (!Objects.equals(user.getPassword(), MD5Util.MD5EncodeUtf8(password))) {
            throw new BusinessException(1, "密码错误");
        }

        return user;
    }

    @Override
    @Transactional
    public void signUp(UserDTO userDTO) {

        if (!checkValid(userDTO.getUsername(), Const.USERNAME) && !checkValid(userDTO.getEmail(), Const.EMAIL)) {
            throw new BusinessException(1, "已存在此用户");
        }

        //设置userRole
        User user = userDTO.converToUser();

        //插入db
        userMapper.insert(user);
    }

    @Override
    public boolean checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            User user;
            if (Objects.equals(type, Const.USERNAME)) {
                user = userMapper.selectByUsername(str);
            } else {
                user = userMapper.selectByEmail(str);
            }
            return user == null;
        }
        return false;
    }

    @Override
    public boolean isAdminPermission(String username){
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new BusinessException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getDesc());
        }
        if (!Objects.equals(Const.Role.ROLE_ADMIN, user.getRole())) {
            throw new BusinessException(ResponseCode.PERMISSION_DEFINDE.getCode(), ResponseCode.PERMISSION_DEFINDE.getDesc());
        }
        return true;
    }

}
