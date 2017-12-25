package com.alchemist.service.impl;

import com.alchemist.common.ResponseCode;
import com.alchemist.dao.CategoryMapper;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.Category;
import com.alchemist.service.ICategoryService;
import com.alchemist.service.IUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Baowen on 2017/12/3.
 */
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private IUserService iUserService;

    @Override
    @Transactional
    public void addCategory(Integer parentId, String categoryName, String username) {
        iUserService.isAdminPermission(username);
        Category category = new Category(parentId, categoryName, true);
        categoryMapper.insert(category);
    }

    @Override
    @Transactional
    public void updateCategory(Integer categoryId, String categoryName, String username) {
        iUserService.isAdminPermission(username);
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null) {
            throw new BusinessException(ResponseCode.CATEGORY_NOT_FOUND.getCode(), ResponseCode.CATEGORY_NOT_FOUND.getDesc());
        }

        category.setName(categoryName);
        categoryMapper.updateByPrimaryKey(category);
    }

    @Override
    public List<Category> getCategoryByParentId(int parentId, String username) {
//        iUserService.isAdminPermission(username); // TODO: 2017/12/10 添加注解权限校验 或者 拦截器
        return categoryMapper.selectByParentId(parentId);
    }

    @Override
    public List<Integer> getDeepCategoryByParentId(int categoryId, String username) {
//        iUserService.isAdminPermission(username);
        Set<Category> categories = Sets.newHashSet();
        findCategoryDeepByCategoryId(categories, categoryId);
        final List<Integer> idList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(categories)) {
            categories.forEach(category -> idList.add(category.getId()));
        }
        return idList;
    }

    private Set<Category> findCategoryDeepByCategoryId(Set<Category> categories, int categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categories.add(category);
        }
        List<Category> categoryList = categoryMapper.selectByParentId(categoryId);
        for (Category c : categoryList) {
            findCategoryDeepByCategoryId(categories, c.getId());
        }
        return categories;
    }
}
