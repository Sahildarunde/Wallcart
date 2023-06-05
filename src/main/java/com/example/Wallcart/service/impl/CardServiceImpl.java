package com.example.Wallcart.service.impl;

import com.example.Wallcart.dao.CustomerRepository;
import com.example.Wallcart.dto.RequestDto.CardRequestDto;
import com.example.Wallcart.dto.RequestDto.CustomerRequestDto;
import com.example.Wallcart.dto.ResponseDto.CardResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;
import com.example.Wallcart.model.Card;
import com.example.Wallcart.model.Customer;
import com.example.Wallcart.service.CardService;
import com.example.Wallcart.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmailId(cardRequestDto.getEmailId());
        if(customer==null){
            throw new CustomerNotFoundException("Invalid Email Id!!");
        }

        //dto --> entity
        Card card = CardTransformer.cardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);
        customer.getCards().add(card);

        //save customer & card
        Customer savedCustomer = customerRepository.save(customer);

        //prepare the response dto
        return CardTransformer.cardToCardResponseDto(card);
    }
}
