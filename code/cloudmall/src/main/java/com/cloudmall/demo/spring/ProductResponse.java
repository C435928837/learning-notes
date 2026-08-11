package com.cloudmall.demo.spring;

import java.math.BigDecimal;

public record ProductResponse(String productCode,
                              String productName,
                              BigDecimal price) {
}
