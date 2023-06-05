package com.example.Wallcart.transformer;

import com.example.Wallcart.dto.RequestDto.CardRequestDto;
import com.example.Wallcart.dto.ResponseDto.CardResponseDto;
import com.example.Wallcart.model.Card;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CardTransformer {

    public Card cardRequestDtoToCard(CardRequestDto cardRequestDto){
        return Card.builder()
                .cardNo(cardRequestDto.getCardNo())
                .validTill(cardRequestDto.getValidTill())
                .cvv(cardRequestDto.getCvv())
                .cardType(cardRequestDto.getCardType())
                .build();
    }

    public static CardResponseDto cardToCardResponseDto(Card card) {

        return CardResponseDto.builder()
                .cardNo(card.getCardNo())
                .cardType(card.getCardType())
                .customerName(card.getCustomer().getName())
                .build();
    }
}
