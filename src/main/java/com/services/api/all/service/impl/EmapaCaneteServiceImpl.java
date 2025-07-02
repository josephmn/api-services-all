package com.services.api.all.service.impl;

import com.services.api.all.dto.WaterAccountStatementResponse;
import com.services.api.all.service.EmapaCaneteService;
import com.services.api.all.util.HtmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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
                .map(HtmlParser::extraerRecibos)
                .flatMapMany(Flux::fromIterable);
    }
}
