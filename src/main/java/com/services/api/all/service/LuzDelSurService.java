package com.services.api.all.service;

import com.services.api.all.dto.*;
import reactor.core.publisher.Mono;

public interface LuzDelSurService {

    Mono<LoginResponse> login(LoginRequest login);
    Mono<LightAccountStatementResponse> getAccountStatement(String supply);
    Mono<byte[]> getPdf(TicketRequest ticket);
    Mono<BillingResponse> getLastBilling(BillingRequest billingRequest);
}
