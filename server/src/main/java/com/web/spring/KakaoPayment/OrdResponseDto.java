package com.web.spring.KakaoPayment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OrdResponseDto {

    private Long ordId;
    private Long clientId;
    private Long boardId;
    private String name;
    private String address;
    private String phone;
    private int totalPrice;
    private int quantity;
    private LocalDate createdAt;
}
