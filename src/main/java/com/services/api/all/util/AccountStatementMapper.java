package com.services.api.all.util;

import com.services.api.all.dto.AccountStatementResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountStatementMapper {

    public static AccountStatementResponse mapToAccountStatementResponse(JSONObject datos) {
        AccountStatementResponse response = new AccountStatementResponse();

        response.setNombre(datos.optString("nombre"));
        response.setSuministro(datos.optString("suministro"));
        response.setMensajeEstadoCuenta(datos.optString("mensajeEstadoCuenta"));
        response.setDireccion(datos.optString("direccion"));

        JSONArray estadoCuentaArray = datos.optJSONArray("estadoCuenta");
        List<AccountStatementResponse.EstadoCuenta> estadoCuentaList = new ArrayList<>();

        if (estadoCuentaArray != null) {
            for (int i = 0; i < estadoCuentaArray.length(); i++) {
                JSONObject item = estadoCuentaArray.getJSONObject(i);
                AccountStatementResponse.EstadoCuenta estado = new AccountStatementResponse.EstadoCuenta();

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
