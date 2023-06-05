package com.example.Wallcart.service;

import com.example.Wallcart.dto.RequestDto.CustomerRequestDto;
import com.example.Wallcart.dto.ResponseDto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto);

    public List<CustomerResponseDto> getFemaleCustomer();

    List<CustomerResponseDto> customersWithKOrders(int k);
}
