package com.alchemist.service.impl;

import com.alchemist.common.Response;
import com.alchemist.common.ResponseCode;
import com.alchemist.dao.CategoryMapper;
import com.alchemist.dao.UserMapper;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.Category;
import com.alchemist.pojo.User;
import com.alchemist.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created by Baowen on 2017/12/3.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService{

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public void addCategory(Integer parentId, String categoryName, String username){
        User user = userMapper.selectByUsername(username);
        if(user == null){
            throw new BusinessException(ResponseCode.USER_NOT_FOUND.getCode(), ResponseCode.USER_NOT_FOUND.getDesc());
        }

        Category category = new Category(parentId, categoryName, true);
        categoryMapper.insert(category);
    }

    @Override
    @Transactional
    public void updateCategory(Integer categoryId, String categoryName, String username){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category == null){
            throw new BusinessException(ResponseCode.CATEGORY_NOT_FOUND.getCode(), ResponseCode.CATEGORY_NOT_FOUND.getDesc());
        }

        category.setName(categoryName);
        categoryMapper.updateByPrimaryKey(category);
    }
}
