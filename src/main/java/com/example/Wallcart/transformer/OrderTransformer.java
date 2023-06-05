package com.example.Wallcart.transformer;

import com.example.Wallcart.dto.ResponseDto.ItemResponseDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.model.Customer;
import com.example.Wallcart.model.Item;
import com.example.Wallcart.model.OrderEntity;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@UtilityClass
public class OrderTransformer {
    public OrderEntity OrderRequestDtoToOrder(Item item, Customer customer){
        return OrderEntity.builder()
                .orderNo(String.valueOf(UUID.randomUUID()))
                .customer(customer)
                .items(new ArrayList<>())
                .totalValue(item.getRequiredQuantity() * item.getProduct().getPrice())
                .build();
    }
    public OrderResponseDto OrderToOrderResponseDto(OrderEntity orderEntity){
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();
        for(Item item : orderEntity.getItems()){
            itemResponseDtos.add(ItemTransformer.itemToItemResponseDto(item));
        }
        return OrderResponseDto.builder()
                .orderdate(orderEntity.getOrderDate())
                .cardUsed(orderEntity.getCardUsed())
                .customerName(orderEntity.getCustomer().getName())
                .totalValue(orderEntity.getTotalValue())
                .orderNo(orderEntity.getOrderNo())
                .items(itemResponseDtos)
                .build();
    }
}
