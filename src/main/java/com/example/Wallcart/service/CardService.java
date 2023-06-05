package com.example.Wallcart.service;

import com.example.Wallcart.dto.RequestDto.CardRequestDto;
import com.example.Wallcart.dto.ResponseDto.CardResponseDto;
import com.example.Wallcart.exception.CustomerNotFoundException;

public interface CardService {

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException;
}
