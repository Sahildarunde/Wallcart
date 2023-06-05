package com.example.Wallcart.dto.RequestDto;

import com.example.Wallcart.Enum.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDto {

    String sellerEmailId;

    String name;

    Category category;

    Integer price;

    Integer quantity;
}
