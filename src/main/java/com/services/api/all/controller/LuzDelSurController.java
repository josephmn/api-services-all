package com.services.api.all.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
 * LuzDelSurController handles requests related to Luz del Sur services.
 * It provides endpoints for user login, account statements, PDF generation, and billing information.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@RestController
@RequestMapping("/api/v1/luzdelsur")
@RequiredArgsConstructor
public class LuzDelSurController {

    private final LuzDelSurService luzDelSurService;

    @PostMapping("/token")
    public Mono<ResponseEntity<LoginResponse>> getToken(@RequestBody LoginRequest login) {
        return this.luzDelSurService.getLogin(login)
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
                    final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    final String filename = "recibo_" + ticket.getSuministro() + "_" + timestamp + ".pdf";
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
