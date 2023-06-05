package com.example.Wallcart.service;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.dto.RequestDto.ProductRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundExecption;

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price);

    List<ProductResponseDto> getAllProductsByCategory(Category category);

    List<ProductResponseDto> getProductsByCategoryAbove500(Category category);

    List<ProductResponseDto> getTop5CheapProducts(Category category);

    List<ProductResponseDto> getTop5CostliestProducts(Category category);

    List<ProductResponseDto> getAllProductsBySeller(String emailId);

    List<ProductResponseDto> getOutOfStockProductsByCategory(Category category);
}
