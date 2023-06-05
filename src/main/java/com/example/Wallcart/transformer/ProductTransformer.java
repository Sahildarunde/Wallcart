package com.example.Wallcart.transformer;

import com.example.Wallcart.Enum.ProductStatus;
import com.example.Wallcart.dto.RequestDto.ProductRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductTransformer {

    public Product productRequestDtoToProduct(ProductRequestDto productRequestDto){
        return Product.builder()
                .name(productRequestDto.getName())
                .category(productRequestDto.getCategory())
                .quantity(productRequestDto.getQuantity())
                .price(productRequestDto.getPrice())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }
    public ProductResponseDto productToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .productName(product.getName())
                .sellerName(product.getSeller().getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .build();
    }

}
