package com.alchemist.service.impl;

import com.alchemist.dao.ProductMapper;
import com.alchemist.pojo.Product;
import com.alchemist.service.IProductService;
import com.google.common.base.Splitter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Baowen on 2017/12/10.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    private static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();

    @Resource
    private ProductMapper productMapper;

    @Override
    @Transactional
    public void saveOrUpdateProduct(Product product) {
        if (product != null) {
            String subImages = product.getSubImages();
            if (StringUtils.isNotBlank(subImages)) {
                List<String> subImageList = splitter.splitToList(subImages);
                if (CollectionUtils.isNotEmpty(subImageList)) {
                    product.setMainImage(subImageList.get(0));
                }

                if (product.getId() != null) {
                    productMapper.updateByPrimaryKey(product);
                } else {
                    productMapper.insert(product);
                }
            }
        }
    }

    @Override
    @Transactional
    public void updateStatus(int productId, int status) {
        productMapper.updateByPrimaryKey(new Product(productId, status));
    }

}
