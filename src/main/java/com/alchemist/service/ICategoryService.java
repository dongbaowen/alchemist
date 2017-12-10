package com.alchemist.service;

import com.alchemist.pojo.Category;

import java.util.List;

/**
 * Created by Baowen on 2017/12/3.
 */
public interface ICategoryService {

    void addCategory(Integer parentId, String categoryName, String username);

    void updateCategory(Integer categoryId, String categoryName, String username);

    List<Category> getCategoryByParentId(int parentId, String username);

    List<Category> getDeepCategoryByParentId(int categoryId, String username);
}
