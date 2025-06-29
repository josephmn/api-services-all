package com.services.api.all.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class BillingRequest {

    public String token;
    public String correo;
    public String suministro;
}
