package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BillingResponse {

    private Facturacion facturacion;
    private Other other;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Facturacion {
        private String ultimaFacturacion;
        private double consumoEnergia;
        private double otrosConceptos;
        private double subTotal;
        private double igv;
        private double noAfectoIGV;
        private double totalPagar;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Other{
        private LocalDate fechaUltimoPago;
        private double saldoPendiente;
        private double deudaVencida;
    }
}
