package com.services.api.all.dto;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * LightAccountStatementResponse class represents a simplified account statement response.
 * It contains basic account information and a list of account statements.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LightAccountStatementResponse {

    private String nombre;
    private String suministro;
    private String mensajeEstadoCuenta;
    private String direccion;
    private List<EstadoCuenta> estadoCuenta;

    /**
     * EstadoCuenta class represents an individual account statement entry.
     * It contains details such as billing cycle, reading dates, amounts, and due dates.
     *
     * @author Joseph Magallanes
     * @since 2025-07-03
     */
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EstadoCuenta {
        private int corrFacturacion;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate fechaLectura;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate fechaEmision;
        @JsonFormat(pattern = "dd-MM-yyyy")
        private LocalDate fechaVencimiento;
        private double importeMes;
        private double deudaVencida;
        private double ajusteRedondeo;
        private double totalPagar;
    }
}
