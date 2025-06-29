package com.services.api.all.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatementResponse {

    private String nombre;
    private String suministro;
    private String mensajeEstadoCuenta;
    private String direccion;
    private List<EstadoCuenta> estadoCuenta;

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
