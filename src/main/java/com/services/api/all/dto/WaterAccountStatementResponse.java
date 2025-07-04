package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * WaterAccountStatementResponse class represents the response for a water account statement.
 * It contains details such as account number, billing information, period, status, debt amount, and receipt URL.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class WaterAccountStatementResponse {

    private int numero;
    private String facturacion;
    private String serie;
    private String periodo;
    private String estado;
    private String deuda;
    private String urlRecibo;
}
