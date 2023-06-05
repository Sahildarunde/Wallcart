package com.example.Wallcart.service.impl;

import com.example.Wallcart.dao.CustomerRepository;
import com.example.Wallcart.dao.ProductRepository;
import com.example.Wallcart.dto.RequestDto.ItemRequestDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.OutOfStockException;
import com.example.Wallcart.exception.ProductNotFoundException;
import com.example.Wallcart.model.Customer;
import com.example.Wallcart.model.Item;
import com.example.Wallcart.model.Product;
import com.example.Wallcart.service.ItemService;
import com.example.Wallcart.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Item createItem(ItemRequestDto itemRequestDto) throws ProductNotFoundException, CustomerNotFoundException, InsufficientQuantityException, OutOfStockException {
        Optional<Product> productOptional = productRepository.findById(itemRequestDto.getProductId());
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product doesn't exists!!");
        }

        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmailId());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exists!!");
        }

        Product product = productOptional.get();

        if(product.getQuantity() < itemRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Sorry! The required quantity is not available");
        }

        if(product.getQuantity() == 0){
            throw new OutOfStockException("Sorry! The product is currently out of stock");
        }

        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto.getRequiredQuantity());



        return item;
    }
}
