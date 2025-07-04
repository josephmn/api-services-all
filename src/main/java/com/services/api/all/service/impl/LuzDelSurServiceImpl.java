package com.services.api.all.service.impl;

import static com.services.api.all.util.AccountStatementMapper.mapToAccountStatementResponse;
import static com.services.api.all.util.BillingMapper.mapToBillingResponse;

import java.util.Base64;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.services.api.all.dto.BillingRequest;
import com.services.api.all.dto.BillingResponse;
import com.services.api.all.dto.LightAccountStatementResponse;
import com.services.api.all.dto.LoginRequest;
import com.services.api.all.dto.LoginResponse;
import com.services.api.all.dto.TicketRequest;
import com.services.api.all.service.LuzDelSurService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * LuzDelSurServiceImpl is the implementation of the LuzDelSurService interface.
 * It provides methods to interact with the Luz del Sur API for user login,
 * account statements, PDF retrieval, and billing information.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Service
@RequiredArgsConstructor
public class LuzDelSurServiceImpl implements LuzDelSurService {

    private final WebClient luzDelSurWebClient;

    @Override
    public Mono<LoginResponse> getLogin(LoginRequest login) {
        final JSONObject requestBody = new JSONObject();
        final JSONObject request = new JSONObject();
        request.put("Correo", login.getCorreo());
        request.put("password", login.getContrasenia());
        request.put("Plataforma", "WEB");
        requestBody.put("request", request);

        return luzDelSurWebClient.post()
                .uri("/es/Login/ValidarAcceso")
                .bodyValue(requestBody.toString())
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    final JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("datos")) {
                        final JSONObject datos = jsonResponse.getJSONObject("datos");
                        final String codigo = datos.optString("codigo");
                        if ("0".equals(codigo)) {
                            return new LoginResponse(true, datos.getString("token"), null);
                        }
                        else {
                            return new LoginResponse(false, null, datos.optString(
                                    "mensajeUsuario", "Error desconocido"));
                        }
                    }
                    return new LoginResponse(false, null, "Respuesta inválida");
                })
                .onErrorReturn(new LoginResponse(false, null, "Error al comunicarse con el servicio"));
    }

    @Override
    public Mono<LightAccountStatementResponse> getAccountStatement(String supply) {
        final JSONObject body = new JSONObject();
        final JSONObject request = new JSONObject();
        request.put("Suministro", supply);
        body.put("request", request);

        return luzDelSurWebClient.post()
                .uri("/es/EstadoCuenta/ObtenerEstadoCuentaLibre")
                .header("Content-Type", "application/json")
                .bodyValue(body.toString())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    final JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.getBoolean("success") && jsonResponse.has("datos")) {
                        final JSONObject datos = jsonResponse.getJSONObject("datos");
                        final String codigo = datos.optString("codigo");
                        if ("0".equals(codigo)) {
                            return Mono.just(mapToAccountStatementResponse(datos));
                        }
                        else {
                            final String mensaje = datos.optString("mensajeUsuario", "Error desconocido");
                            return Mono.error(new RuntimeException(mensaje));
                        }
                    }
                    else {
                        return Mono.error(new RuntimeException("Respuesta inválida del servidor"));
                    }
                })
                .onErrorResume(e -> Mono.error(new RuntimeException(
                        "Error al obtener facturación: " + e.getMessage(), e)));
    }

    @Override
    public Mono<byte[]> getPdf(TicketRequest ticket) {
        final JSONObject body = new JSONObject();
        final JSONObject request = new JSONObject();
        request.put("Suministro", ticket.getSuministro());
        request.put("CorrFacturacion", ticket.getCorrelativo());
        body.put("request", request);

        return luzDelSurWebClient.post()
                .uri("/es/InformacionGuardada/ObtenerBoletaPorMes")
                .header("Content-Type", "application/json")
                .bodyValue(body.toString())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    final JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.optBoolean("success") && jsonResponse.has("datos")) {
                        final JSONObject datos = jsonResponse.getJSONObject("datos");
                        if ("0".equals(datos.optString("codigo"))) {
                            final String archivoBase64 = datos.getString("archivoBase64");
                            final byte[] pdfBytes = Base64.getDecoder().decode(archivoBase64);
                            return Mono.just(pdfBytes);
                        }
                        else {
                            return Mono.error(new RuntimeException(datos.optString(
                                    "mensajeUsuario", "Error desconocido")));
                        }
                    }
                    return Mono.error(new RuntimeException("Respuesta inválida del servidor"));
                })
                .onErrorResume(WebClientResponseException.class,
                        e -> Mono.error(new RuntimeException(
                                "HTTP error: " + e.getRawStatusCode())))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error: " + e.getMessage())));
    }

    @Override
    public Mono<BillingResponse> getLastBilling(BillingRequest billingRequest) {
        final JSONObject body = new JSONObject();
        final JSONObject request = new JSONObject();
        request.put("Token", billingRequest.getToken());
        request.put("Correo", billingRequest.getCorreo());
        request.put("Suministro", billingRequest.getSuministro());
        body.put("request", request);

        return luzDelSurWebClient.post()
                .uri("/es/InformacionGuardada/ObtenerUltimaFacturacion")
                .header("Content-Type", "application/json")
                .bodyValue(body.toString())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    final JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.optBoolean("success") && jsonResponse.has("datos")) {
                        final JSONObject datos = jsonResponse.getJSONObject("datos");
                        final String codigo = datos.optString("codigo");
                        if ("0".equals(codigo)) {
                            return Mono.just(mapToBillingResponse(datos));
                        }
                        else {
                            final String mensaje = datos.optString("mensajeUsuario", "Error desconocido");
                            return Mono.error(new RuntimeException(mensaje));
                        }
                    }
                    else {
                        return Mono.error(new RuntimeException("Respuesta inválida del servidor"));
                    }
                })
                .onErrorResume(e -> Mono.error(new RuntimeException(
                        "Error al obtener facturación: " + e.getMessage(), e)));
    }
}
