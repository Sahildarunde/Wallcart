package com.example.Wallcart.service;

import com.example.Wallcart.dto.RequestDto.ItemRequestDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.OutOfStockException;
import com.example.Wallcart.exception.ProductNotFoundException;
import com.example.Wallcart.model.Item;

import java.security.PublicKey;

public interface ItemService {

    public Item createItem(ItemRequestDto itemRequestDto) throws ProductNotFoundException, CustomerNotFoundException, InsufficientQuantityException, OutOfStockException;
}
