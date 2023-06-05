package com.example.Wallcart.dto.RequestDto;

import com.example.Wallcart.Enum.CardType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {

    String emailId;

    String cardNo;

    int cvv;

    CardType cardType;

    Date validTill;
}
