package com.example.Wallcart.dto.RequestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderRequestDto{

    String emailId;

    int productId;

    int cvv;

    String cardNo;

    int requiredQuantity;
}
