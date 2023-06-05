package com.example.Wallcart.transformer;

import com.example.Wallcart.dto.ResponseDto.CartResponseDto;
import com.example.Wallcart.dto.ResponseDto.ItemResponseDto;
import com.example.Wallcart.model.Cart;
import com.example.Wallcart.model.Item;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CartTransformer {
    public CartResponseDto cartToCartResponseDto(Cart cart){
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        for(Item item : cart.getItems()){
            itemResponseDtos.add(ItemTransformer.itemToItemResponseDto(item));
        }
        return CartResponseDto.builder()
                .cartTotal(cart.getCartTotal())
                .customerName(cart.getCustomer().getName())
                .items(itemResponseDtos)
                .build();
    }
}
