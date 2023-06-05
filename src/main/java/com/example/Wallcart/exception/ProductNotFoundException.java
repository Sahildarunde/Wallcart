package com.example.Wallcart.exception;

import com.example.Wallcart.dao.ProductRepository;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(String message){
        super(message);
    }
}
