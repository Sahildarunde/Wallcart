package com.example.Wallcart.service.impl;

import com.example.Wallcart.Enum.ProductStatus;
import com.example.Wallcart.dao.CardRepository;
import com.example.Wallcart.dao.CustomerRepository;
import com.example.Wallcart.dao.OrderRepository;
import com.example.Wallcart.dao.ProductRepository;
import com.example.Wallcart.dto.RequestDto.OrderRequestDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.InvalidCardException;
import com.example.Wallcart.exception.ProductNotFoundException;
import com.example.Wallcart.model.*;
import com.example.Wallcart.service.OrderService;
import com.example.Wallcart.transformer.ItemTransformer;
import com.example.Wallcart.transformer.OrderTransformer;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException, InvalidCardException {
        Customer customer = customerRepository.findByEmailId(orderRequestDto.getEmailId());
        if(customer==null){
            throw new CustomerNotFoundException("Customer doesn't exists!!");
        }

        Optional<Product> optionalProduct = productRepository.findById(orderRequestDto.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Sorry! Product doesn't exist.");
        }
        Product product = optionalProduct.get();

        //check for quantity

        if(product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Sorry! Required quantity not available");
        }

        // card
        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());
        Date date = new Date();
        if(card==null || card.getCvv() != orderRequestDto.getCvv() || date.after(card.getValidTill())){
            throw new InvalidCardException("Sorry! Card is not valid");
        }
        int newQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        product.setQuantity(newQuantity);
        if(newQuantity == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }

        //create the item first
        Item item = ItemTransformer.itemRequestDtoToItem(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);

        OrderEntity orderEntity = OrderTransformer.OrderRequestDtoToOrder(item, customer);
        String maskedCard = generateMaskedCardNo(card);
        orderEntity.setCardUsed(maskedCard);
        orderEntity.getItems().add(item);
        item.setOrderEntity(orderEntity);

        OrderEntity savedOrder = orderRepository.save(orderEntity); //saves both order & item

        customer.getOrders().add(savedOrder);
        product.getItems().add(savedOrder.getItems().get(0));

        //prepare the response dto
        return OrderTransformer.OrderToOrderResponseDto(savedOrder);
    }

    public OrderEntity placeOrder(Cart cart, Card card) throws InsufficientQuantityException {

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderNo(String.valueOf(UUID.randomUUID()));
        orderEntity.setCardUsed(generateMaskedCardNo(card));

        int totalValue = 0;
        for(Item item : cart.getItems()){
            Product product = item.getProduct();
            if(item.getRequiredQuantity()>product.getQuantity()){
                throw new InsufficientQuantityException("Required quantity noty available!!");
            }
            totalValue += item.getRequiredQuantity() * product.getPrice();
            int newQuantity = product.getQuantity() - item.getRequiredQuantity();
            product.setQuantity(newQuantity);
            if(newQuantity==0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            item.setOrderEntity(orderEntity);
        }

        orderEntity.setTotalValue(totalValue);
        orderEntity.setItems(cart.getItems());
        orderEntity.setCustomer(cart.getCustomer());
        return orderEntity;
    }
    private String  generateMaskedCardNo(Card card){
        String cardNo = "";
        String orginalCardNo = card.getCardNo();

        for(int i=0;i<orginalCardNo.length()-4;i++){
            cardNo += "x";
        }
        cardNo += orginalCardNo.substring(orginalCardNo.length()-4);
        return cardNo;
    }
}
