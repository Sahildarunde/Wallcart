package com.example.Wallcart.controller;

import com.example.Wallcart.dao.OrderRepository;
import com.example.Wallcart.dto.RequestDto.CheckoutCartRequestDto;
import com.example.Wallcart.dto.RequestDto.ItemRequestDto;
import com.example.Wallcart.dto.ResponseDto.CartResponseDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.OutOfStockException;
import com.example.Wallcart.exception.ProductNotFoundException;
import com.example.Wallcart.model.Item;
import com.example.Wallcart.service.CardService;
import com.example.Wallcart.service.CartService;
import com.example.Wallcart.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ItemService itemSevice;
    @Autowired
    CartService cartService;
    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto){
        try{
            Item item = itemSevice.createItem(itemRequestDto);
            CartResponseDto cartResponseDto = cartService.addToCart(item, itemRequestDto);
            return new ResponseEntity(cartResponseDto, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity checkoutCart(@RequestBody CheckoutCartRequestDto checkoutCartRequestDto){
        try{
            OrderResponseDto orderResponseDto = cartService.checkoutCart(checkoutCartRequestDto);
            return new ResponseEntity(orderResponseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
