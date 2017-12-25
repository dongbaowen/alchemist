package com.alchemist.service.impl;

import com.alchemist.common.Const;
import com.alchemist.dao.CategoryMapper;
import com.alchemist.dao.ProductMapper;
import com.alchemist.exception.BusinessException;
import com.alchemist.pojo.Category;
import com.alchemist.pojo.Product;
import com.alchemist.service.ICategoryService;
import com.alchemist.service.IProductService;
import com.alchemist.util.PropertiesUtil;
import com.alchemist.vo.ProductDetailVo;
import com.alchemist.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by Baowen on 2017/12/10.
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {

    private static final Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ICategoryService iCategoryService;

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

    @Override
    public ProductDetailVo manageProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            throw new BusinessException(1, "");
        }
        return new ProductDetailVo(product, PropertiesUtil.getProperty("ftp.server.http.prefix"));
    }


    @Override
    public PageInfo getProductList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix");
        List<Product> productList = productMapper.selectAll();
        List<ProductListVo> voList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(voList)) {
            productList.forEach(product -> voList.add(new ProductListVo(product, imageHost)));
        }
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(voList);
        return pageInfo;
    }

    @Override
    public PageInfo searchProductList(String productName, Integer productId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix");
        List<Product> productList = productMapper.selectByProductName(productName, productId);

        List<ProductListVo> voList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(productList)) {
            productList.forEach(product -> voList.add(new ProductListVo(product, imageHost)));
        }
        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(voList);
        return pageInfo;
    }

    @Override
    public ProductDetailVo getProductDetail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || !Objects.equals(product.getStatus(), Const.ProductStatusEnum.ON_SALE.getCode())) {
            throw new BusinessException(1, "商品不存在或者已下架");
        }
        return new ProductDetailVo(product, PropertiesUtil.getProperty("ftp.server.http.prefix"));
    }

    @Override
    public PageInfo getProductList(String keyWords, Integer categoryId, Integer pageNum, Integer pageSize, String orderBy) {
        List<Integer> idList = Lists.newArrayList();
        PageInfo<ProductListVo> pageInfo;
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyWords)) {
                PageHelper.startPage(pageNum, pageSize);
                pageInfo = new PageInfo<>(Lists.newArrayList());
                return pageInfo;
            }
            idList = iCategoryService.getDeepCategoryByParentId(categoryId, keyWords);
        }
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }
        List<Product> products = productMapper.selectByNameAndCategoryIds(keyWords, idList);
        List<ProductListVo> productVoList = Lists.newArrayList();
        products.forEach(p -> productVoList.add(new ProductListVo(p, PropertiesUtil.getProperty("ftp.server.http.prefix"))));
        pageInfo = new PageInfo<>(productVoList);
        return pageInfo;
    }
}
