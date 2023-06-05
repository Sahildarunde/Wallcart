package com.example.Wallcart.service.impl;

import com.example.Wallcart.dao.*;
import com.example.Wallcart.dto.RequestDto.CheckoutCartRequestDto;
import com.example.Wallcart.dto.RequestDto.ItemRequestDto;
import com.example.Wallcart.dto.ResponseDto.CartResponseDto;
import com.example.Wallcart.dto.ResponseDto.OrderResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.exception.EmptyCartException;
import com.example.Wallcart.exception.InsufficientQuantityException;
import com.example.Wallcart.exception.InvalidCardException;
import com.example.Wallcart.model.*;
import com.example.Wallcart.service.CartService;
import com.example.Wallcart.service.OrderService;
import com.example.Wallcart.transformer.CartTransformer;
import com.example.Wallcart.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;


@Service
public class CartServiceImpl implements CartService {

    @Autowired
    OrderService orderService;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;
    @Override
    public CartResponseDto addToCart(Item item, ItemRequestDto itemRequestDto) {

        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmailId());
        Product product = productRepository.findById(itemRequestDto.getProductId()).get();

        Cart cart = customer.getCart();
        cart.setCartTotal(cart.getId()+ item.getRequiredQuantity()* product.getPrice());
        cart.getItems().add(item);
        item.setCart(cart);
        item.setProduct(product);

        Cart savedCart = cartRepository.save(cart); //saves both cart & item
        Item savedItem = cart.getItems().get(cart.getItems().size()-1);
        product.getItems().add(savedItem);

        //prepare response dto
        return CartTransformer.cartToCartResponseDto(savedCart);

    }

    @Override
    public OrderResponseDto checkoutCart(CheckoutCartRequestDto checkoutCartRequestDto) throws CustomerNotFoundException, InvalidCardException, EmptyCartException, InsufficientQuantityException {
        Customer customer = customerRepository.findByEmailId(checkoutCartRequestDto.getEmailId());
        if(customer == null){
            throw new CustomerNotFoundException("Customer doesn't exist");
        }

        Card card = cardRepository.findByCardNo(checkoutCartRequestDto.getCardNo());
        Date date = new Date();
        if(card==null || card.getCvv() != checkoutCartRequestDto.getCvv() || date.after(card.getValidTill())){
            throw new InvalidCardException("Sorry! Card is not valid");
        }

        Cart cart = customer.getCart();
        if(cart.getItems().size()==0){
            throw new EmptyCartException("Cart is empty!!");
        }

        try {
            OrderEntity order = orderService.placeOrder(cart, card);
            resetCart(cart);

            OrderEntity savedOrder = orderRepository.save(order);
            customer.getOrders().add(savedOrder);
            return OrderTransformer.OrderToOrderResponseDto(savedOrder);
        }catch (InsufficientQuantityException e){
            throw e;
        }

    }
    private void resetCart(Cart cart){
        cart.setCartTotal(0);
        for(Item item : cart.getItems()){
            item.setCart(null);
        }
        cart.setItems(new ArrayList<>());
    }
}
