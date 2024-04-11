package com.scaler.ecompaymentservice.dtos;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InitiatePaymentRequestDto {
    private Long orderId;
    private Long amount;
    private String phoneNumber;
}