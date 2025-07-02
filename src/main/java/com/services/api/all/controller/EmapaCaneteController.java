package com.services.api.all.controller;

import com.services.api.all.dto.WaterAccountStatementResponse;
import com.services.api.all.service.EmapaCaneteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/emapacanete")
@RequiredArgsConstructor
public class EmapaCaneteController {

    private final EmapaCaneteService emapaCaneteService;

    @PostMapping("/accountStatement")
    public Mono<ResponseEntity<Flux<WaterAccountStatementResponse>>> getWaterAccountStatement(
            @RequestParam String codsuc, @RequestParam String nroinscripcion) {
        return Mono.just(ResponseEntity.ok(this.emapaCaneteService.getAccountStatement(codsuc, nroinscripcion)))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest()
                        .header("X-Error", throwable.getMessage())
                        .build()));
    }
}
