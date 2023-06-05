package com.example.Wallcart.service;

import com.example.Wallcart.dto.RequestDto.CheckoutCartRequestDto;
import com.example.Wallcart.dto.RequestDto.ItemRequestDto;
import com.example.Wallcart.dto.ResponseDto.CartResponseDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.EmptyCartException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.InvalidCardException;
import com.example.Wallcart.model.Item;

public interface CartService {

    public CartResponseDto addToCart(Item item, ItemRequestDto itemRequestDto);

    public OrderResponseDto checkoutCart(CheckoutCartRequestDto checkoutCartRequestDto) throws CustomerNotFoundException, InvalidCardException, EmptyCartException, InsufficientQuantityException;
}
