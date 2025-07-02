package com.services.api.all.controller;

import com.services.api.all.dto.*;
import com.services.api.all.service.LuzDelSurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/luzdelsur")
@RequiredArgsConstructor
public class LuzDelSurController {

    private final LuzDelSurService luzDelSurService;

    @PostMapping("/token")
    public Mono<ResponseEntity<LoginResponse>> getToken(@RequestBody LoginRequest login) {
        return this.luzDelSurService.login(login)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest()
                        .header("X-Error", throwable.getMessage())
                        .build()));
    }

    @PostMapping("/accountStatement/{supply}")
    public Mono<ResponseEntity<LightAccountStatementResponse>> getLightAccountStatement(@PathVariable String supply) {
        return this.luzDelSurService.getAccountStatement(supply)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest()
                        .header("X-Error", throwable.getMessage())
                        .build()));
    }

    @PostMapping("/pdf")
    public Mono<ResponseEntity<byte[]>> getPdf(@RequestBody TicketRequest ticket) {
        return luzDelSurService.getPdf(ticket)
                .map(pdfBytes -> {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    String filename = "recibo_" + ticket.getSuministro() + "_" + timestamp + ".pdf";
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "inline; filename=" + filename)
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(pdfBytes);
                })
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest()
                                .header("X-Error", e.getMessage())
                                .build()
                ));
    }

    @PostMapping("/lastBilling")
    public Mono<ResponseEntity<BillingResponse>> getLastBilling(@RequestBody BillingRequest billingRequest) {
        return this.luzDelSurService.getLastBilling(billingRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest()
                        .header("X-Error", throwable.getMessage())
                        .build()));
    }
}
