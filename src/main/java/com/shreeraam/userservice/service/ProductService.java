package com.shreeraam.userservice.service;

import com.shreeraam.userservice.model.Product;
import com.shreeraam.userservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts(int page, int pageSize) {
        // products contains pagination meta-data
        Page<Product> products = productRepository.findAll(PageRequest.of(page, pageSize));
        return products.getContent();
    }

}
