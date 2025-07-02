package com.services.api.all.util;

import com.services.api.all.dto.LightAccountStatementResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountStatementMapper {

    public static LightAccountStatementResponse mapToAccountStatementResponse(JSONObject datos) {
        LightAccountStatementResponse response = new LightAccountStatementResponse();

        response.setNombre(datos.optString("nombre"));
        response.setSuministro(datos.optString("suministro"));
        response.setMensajeEstadoCuenta(datos.optString("mensajeEstadoCuenta"));
        response.setDireccion(datos.optString("direccion"));

        JSONArray estadoCuentaArray = datos.optJSONArray("estadoCuenta");
        List<LightAccountStatementResponse.EstadoCuenta> estadoCuentaList = new ArrayList<>();

        if (estadoCuentaArray != null) {
            for (int i = 0; i < estadoCuentaArray.length(); i++) {
                JSONObject item = estadoCuentaArray.getJSONObject(i);
                LightAccountStatementResponse.EstadoCuenta estado = new LightAccountStatementResponse.EstadoCuenta();

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

    public static LocalDate parseDate(String dateStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }
}
