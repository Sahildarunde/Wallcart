package com.example.Wallcart.service.impl;

import com.example.Wallcart.dao.CustomerRepository;
import com.example.Wallcart.dto.RequestDto.CustomerRequestDto;
import com.example.Wallcart.dto.ResponseDto.CustomerResponseDto;
import com.example.Wallcart.model.Cart;
import com.example.Wallcart.model.Customer;
import com.example.Wallcart.service.CustomerService;
import com.example.Wallcart.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired CustomerRepository customerRepository;
    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
        //dto --> entity
        Customer customer = CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();
        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);

        //prepare the response dto
        return CustomerTransformer.customerToCustomerResponseDto(savedCustomer);

    }

    @Override
    public List<CustomerResponseDto> getFemaleCustomer() {
        List<Customer> customers = customerRepository.findFemaleCustomerByAge();
        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        for(Customer customer : customers){
            customerResponseDtos.add(CustomerTransformer.customerToCustomerResponseDto(customer));
        }
        return customerResponseDtos;
    }

    @Override
    public List<CustomerResponseDto> customersWithKOrders(int k) {
        List<Customer> customers = customerRepository.customersWithKOrders();
        List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
        for (Customer customer : customers){
            if(customer.getOrders().size() >= k)
                customerResponseDtos.add(CustomerTransformer.customerToCustomerResponseDto(customer));
        }
        return customerResponseDtos;
    }
}
