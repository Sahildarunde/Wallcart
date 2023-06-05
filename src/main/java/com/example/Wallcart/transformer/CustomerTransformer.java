package com.example.Wallcart.transformer;

import com.example.Wallcart.dto.RequestDto.CustomerRequestDto;
import com.example.Wallcart.dto.ResponseDto.CustomerResponseDto;
import com.example.Wallcart.model.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerTransformer {

    public Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder()
                .name(customerRequestDto.getName())
                .mobNo(customerRequestDto.getMobNo())
                .emailId(customerRequestDto.getEmailId())
                .gender(customerRequestDto.getGender())
                .build();
    }
    public CustomerResponseDto customerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .name(customer.getName())
                .mobNo(customer.getMobNo())
                .emailId(customer.getEmailId())
                .build();
    }
}
