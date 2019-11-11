package com.gaolei.crawler.service.impl;

import com.gaolei.crawler.dao.ProductDao;
import com.gaolei.crawler.pojo.Product;
import com.gaolei.crawler.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author gaolei
 * @version 1.0
 * @date 2019/11/6 16:06
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    //新增产品
    @Override
    @Transactional
    public void addProduct(Product product) {
        productDao.saveAndFlush(product);
    }

    //查找所有符合条件的产品
    @Override
    public List<Product> findProducts(Product productInfo) {
        Example<Product> example = Example.of(productInfo);
        return productDao.findAll(example);
    }

    //删除符合条件的产品
    @Override
    @Transactional
    public void deleteProduct(Product product) {
        productDao.deleteById(product.getProduct_id());
    }

    //根据产品的最新信息更改产品(产品ID不会变,变啥改啥,不变的不改)
    @Override
    @Transactional
    public void updateProduct(Product product) {
        productDao.updateById(product);
    }

    @Transactional
    public void findOne() {
        Specification<Product> spec = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> product_id = root.get("product_id");
                Predicate predicate = criteriaBuilder.equal(product_id, "sfsffffffff");
                return predicate;
            }
        };
        Product product = productDao.findOne(spec).get();
    }
}
