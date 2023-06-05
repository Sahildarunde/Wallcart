package com.example.Wallcart.controller;

import com.example.Wallcart.dto.RequestDto.CustomerRequestDto;
import com.example.Wallcart.dto.ResponseDto.CustomerResponseDto;
import com.example.Wallcart.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        CustomerResponseDto customerResponseDto = customerService.addCustomer(customerRequestDto);
        return new ResponseEntity(customerResponseDto, HttpStatus.CREATED);
    }

    //get all female customer between age 20-30
    @GetMapping("/get/female")
    public ResponseEntity getFemalesCustomer(){
        List<CustomerResponseDto> customerResponseDtos = customerService.getFemaleCustomer();
        return new ResponseEntity(customerResponseDtos, HttpStatus.FOUND);
    }


    //customer who have atleast k products
    @GetMapping("/get/{k}")
    public ResponseEntity customersWithKOrders(@PathVariable int k){
        List<CustomerResponseDto> customerResponseDtos = customerService.customersWithKOrders(k);
        return new ResponseEntity<>(customerResponseDtos, HttpStatus.FOUND);
    }
}
