package com.example.Wallcart.service;

import com.example.Wallcart.Enum.Category;
import com.example.Wallcart.dto.RequestDto.SellerRequestDto;
import com.example.Wallcart.dto.ResponseDto.ProductResponseDto;
import com.example.Wallcart.dto.ResponseDto.SellerResponseDto;
import com.example.Wallcart.exception.SellerNotFoundExecption;
import com.example.Wallcart.model.Seller;

import java.util.List;

public interface SellerService {
    SellerResponseDto addSeller(SellerRequestDto sellerRequestDto);

    SellerResponseDto updateSeller(String emailId, SellerRequestDto sellerRequestDto) throws SellerNotFoundExecption;

    List<SellerResponseDto> getByCategory(Category category);

    SellerResponseDto getSellerWithMostProducts();

    SellerResponseDto getSellerWithLeastProducts();

    SellerResponseDto getSellerWithCostliestProduct();

    SellerResponseDto getSellerWithCheapestProduct();
}
