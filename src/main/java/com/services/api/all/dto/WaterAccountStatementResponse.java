package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
