package com.example.Wallcart.transformer;

import com.example.Wallcart.dto.ResponseDto.ItemResponseDto;
import com.example.Wallcart.model.Customer;
import com.example.Wallcart.model.Item;
import com.example.Wallcart.model.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ItemTransformer {
    public Item itemRequestDtoToItem(int requiredQuantity) {
        return Item.builder()
                .requiredQuantity(requiredQuantity)
                .build();
    }

    public static ItemResponseDto itemToItemResponseDto(Item item) {
        return ItemResponseDto.builder()
                .quantityAdded(item.getRequiredQuantity())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .build();
    }
}
