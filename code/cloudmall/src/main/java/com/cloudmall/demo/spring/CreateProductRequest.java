package com.cloudmall.demo.spring;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateProductRequest(@NotBlank(message = "商品编码不能为空")
                                   @Size(max = 20, message = "商品编码不能超过 20 个字符")
                                   String productCode,

                                   @NotBlank(message = "商品名称不能为空")
                                   @Size(max = 50, message = "商品名称不能超过 50 个字符")
                                   String productName,

                                   @NotNull(message = "商品价格不能为空")
                                   @DecimalMin(value = "0.01", message = "商品价格必须大于 0")
                                   BigDecimal price) {

}
