package com.alchemist.service;

import com.alchemist.pojo.Product;

/**
 * Created by Baowen on 2017/12/10.
 */
public interface IProductService {

    void saveOrUpdateProduct(Product product);

    void updateStatus(int productId, int status);
}
