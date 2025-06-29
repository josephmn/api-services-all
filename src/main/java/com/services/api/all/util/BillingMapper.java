package com.services.api.all.util;

import com.services.api.all.dto.BillingResponse;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BillingMapper {

    public static BillingResponse mapToBillingResponse(JSONObject datos) {
        BillingResponse.Facturacion facturacion = new BillingResponse.Facturacion(
                datos.optString("ultimaFacturacion"),
                datos.optDouble("consumoEnergia"),
                datos.optDouble("otrosConceptos"),
                datos.optDouble("subTotal"),
                datos.optDouble("igv"),
                datos.optDouble("noAfectoIGV"),
                datos.optDouble("totalPagar")
        );

        String fechaUltimoPagoStr = datos.optString("fechaUltimoPago", null);
        LocalDate fechaUltimoPago = null;
        if (fechaUltimoPagoStr != null && !fechaUltimoPagoStr.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaUltimoPago = LocalDate.parse(fechaUltimoPagoStr, formatter);
        }

        BillingResponse.Other other = new BillingResponse.Other(
                fechaUltimoPago,
                datos.optDouble("saldoPendiente"),
                datos.optDouble("deudaVencida")
        );

        return new BillingResponse(facturacion, other);
    }
}
