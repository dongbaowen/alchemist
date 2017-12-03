package com.alchemist.service;

/**
 * Created by Baowen on 2017/12/3.
 */
public interface ICategoryService {

    void addCategory(Integer parentId, String categoryName, String username);

    void updateCategory(Integer categoryId, String categoryName, String username);
}
