package com.services.api.all.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * BillingResponse class represents the response structure for billing information.
 * It contains details about the last billing, energy consumption, and other related information.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class BillingResponse {

    private Facturacion facturacion;
    private Other other;

    /**
     * Default constructor for BillingResponse.
     * Initializes the facturacion and other fields to null.
     */
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

    /**
     * Other class represents additional billing information.
     * It includes the date of the last payment, pending balance, and overdue debt.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Other {
        private LocalDate fechaUltimoPago;
        private double saldoPendiente;
        private double deudaVencida;
    }
}
