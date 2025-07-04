package com.services.api.all.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import com.services.api.all.dto.BillingResponse;

/**
 * Utility class to map JSON data to BillingResponse objects.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
public class BillingMapper {

    /**
     * Maps a JSONObject containing billing data to a BillingResponse object.
     *
     * @param datos the JSONObject containing billing data
     * @return a BillingResponse object populated with the data from the JSONObject
     */
    public static BillingResponse mapToBillingResponse(JSONObject datos) {
        final BillingResponse.Facturacion facturacion = new BillingResponse.Facturacion(
                datos.optString("ultimaFacturacion"),
                datos.optDouble("consumoEnergia"),
                datos.optDouble("otrosConceptos"),
                datos.optDouble("subTotal"),
                datos.optDouble("igv"),
                datos.optDouble("noAfectoIGV"),
                datos.optDouble("totalPagar")
        );

        final String fechaUltimoPagoStr = datos.optString("fechaUltimoPago", null);
        LocalDate fechaUltimoPago = null;
        if (fechaUltimoPagoStr != null && !fechaUltimoPagoStr.isEmpty()) {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaUltimoPago = LocalDate.parse(fechaUltimoPagoStr, formatter);
        }

        final BillingResponse.Other other = new BillingResponse.Other(
                fechaUltimoPago,
                datos.optDouble("saldoPendiente"),
                datos.optDouble("deudaVencida")
        );

        return new BillingResponse(facturacion, other);
    }
}
