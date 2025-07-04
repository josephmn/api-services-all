package com.services.api.all.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.services.api.all.dto.LightAccountStatementResponse;

/**
 * Utility class for mapping JSON data to LightAccountStatementResponse objects.
 * This class provides methods to convert JSON objects containing account statement data
 * into a structured response format.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
public class AccountStatementMapper {

    /**
     * Maps the JSON object containing account statement data to a LightAccountStatementResponse object.
     *
     * @param datos The JSON object containing account statement data.
     * @return A LightAccountStatementResponse object populated with the data from the JSON object.
     */
    public static LightAccountStatementResponse mapToAccountStatementResponse(JSONObject datos) {
        final LightAccountStatementResponse response = new LightAccountStatementResponse();

        response.setNombre(datos.optString("nombre"));
        response.setSuministro(datos.optString("suministro"));
        response.setMensajeEstadoCuenta(datos.optString("mensajeEstadoCuenta"));
        response.setDireccion(datos.optString("direccion"));

        final JSONArray estadoCuentaArray = datos.optJSONArray("estadoCuenta");
        final List<LightAccountStatementResponse.EstadoCuenta> estadoCuentaList = new ArrayList<>();

        if (estadoCuentaArray != null) {
            for (int i = 0; i < estadoCuentaArray.length(); i++) {
                final JSONObject item = estadoCuentaArray.getJSONObject(i);
                final LightAccountStatementResponse.EstadoCuenta estado =
                        new LightAccountStatementResponse.EstadoCuenta();

                estado.setCorrFacturacion(item.getInt("corrFacturacion"));
                estado.setFechaLectura(parseDate(item.optString("fechaLectura")));
                estado.setFechaEmision(parseDate(item.optString("fechaEmision")));
                estado.setFechaVencimiento(parseDate(item.optString("fechaVencimiento")));
                estado.setImporteMes(item.optDouble("importeMes", 0));
                estado.setDeudaVencida(item.optDouble("deudaVencida", 0));
                estado.setAjusteRedondeo(item.optDouble("ajusteRedondeo", 0));
                estado.setTotalPagar(item.optDouble("totalPagar", 0));

                estadoCuentaList.add(estado);
            }
        }

        response.setEstadoCuenta(estadoCuentaList);
        return response;
    }

    /**
     * Parses a date string in the format "dd/MM/yyyy" and returns a LocalDate object.
     * If the parsing fails, it returns null.
     *
     * @param dateStr The date string to parse.
     * @return A LocalDate object representing the parsed date, or null if parsing fails.
     */
    public static LocalDate parseDate(String dateStr) {
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        }
        catch (Exception e) {
            return null;
        }
    }
}
