package com.example.Wallcart.model;

import com.example.Wallcart.Enum.CardType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "card_no",unique = true, nullable = false)
    String cardNo;

    @Column(name = "cvv",unique = true, nullable = false)
    int cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type",unique = true, nullable = false)
    CardType cardType;

    @Column(name = "valid_till",unique = true, nullable = false)
    Date validTill;

    @ManyToOne
    @JoinColumn
    Customer customer;
}
