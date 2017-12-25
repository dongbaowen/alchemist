package com.alchemist.service;

import com.alchemist.pojo.Product;
import com.alchemist.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

/**
 * Created by Baowen on 2017/12/10.
 */
public interface IProductService {

    void saveOrUpdateProduct(Product product);

    void updateStatus(int productId, int status);

    ProductDetailVo manageProductDetail(Integer productId);

    PageInfo getProductList(Integer pageNum, Integer pageSize);

    PageInfo searchProductList(String productName, Integer productId, Integer pageNum, Integer pageSize);

    ProductDetailVo getProductDetail(Integer productId);

    PageInfo getProductList(String keyWords, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy);
}
