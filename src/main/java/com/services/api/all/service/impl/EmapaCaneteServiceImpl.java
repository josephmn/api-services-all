package com.services.api.all.service.impl;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.services.api.all.dto.WaterAccountStatementResponse;
import com.services.api.all.service.EmapaCaneteService;
import com.services.api.all.util.HtmlParser;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * EmapaCaneteServiceImpl provides methods to interact with the Emapa Canete API.
 * It retrieves water account statements based on the provided codSuc and nroInscripcion.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Service
@RequiredArgsConstructor
public class EmapaCaneteServiceImpl implements EmapaCaneteService {

    private final WebClient aguaCaneteWebClient;

    @Override
    public Flux<WaterAccountStatementResponse> getAccountStatement(String codSuc, String nroInscripcion) {
        return aguaCaneteWebClient.post()
                .uri("/cliente/informacion")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("codsuc", codSuc)
                        .with("nroinscripcion", nroInscripcion))
                .retrieve()
                .bodyToMono(String.class)
                .map(HtmlParser::extractReceipts)
                .flatMapMany(Flux::fromIterable);
    }
}
