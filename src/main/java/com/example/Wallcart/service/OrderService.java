package com.example.Wallcart.service;

import com.example.Wallcart.dto.RequestDto.OrderRequestDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.InvalidCardException;
import com.example.Wallcart.exception.ProductNotFoundException;
import com.example.Wallcart.model.Card;
import com.example.Wallcart.model.Cart;
import com.example.Wallcart.model.OrderEntity;

public interface OrderService {

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException, InvalidCardException;

    public OrderEntity placeOrder(Cart cart, Card card) throws InsufficientQuantityException;
}
