package com.cloudmall.demo.spring;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public String getProduct(String productId) throws InterruptedException {
        Thread.sleep(3000);
        return productRepository.getProductById(productId);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.existsByCode(request.productCode())) {
            throw new BusinessException(
                    40901,
                    HttpStatus.CONFLICT,
                    "商品编码已存在"
            );
        }

        productRepository.save(request);

        return new ProductResponse(
                request.productCode(),
                request.productName(),
                request.price()
        );
    }

}
