package com.example.converse.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReq {
    
    private String name;

    private String email;

    private String address;
    
    private String country;

    private String phone;

    private String note;
}
