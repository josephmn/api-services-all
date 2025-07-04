package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * BillingRequest class represents a request for billing information.
 * It contains fields for token, email, and supply.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class BillingRequest {

    public String token;
    public String correo;
    public String suministro;
}
