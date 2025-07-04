package com.services.api.all.service;

import com.services.api.all.dto.BillingRequest;
import com.services.api.all.dto.BillingResponse;
import com.services.api.all.dto.LightAccountStatementResponse;
import com.services.api.all.dto.LoginRequest;
import com.services.api.all.dto.LoginResponse;
import com.services.api.all.dto.TicketRequest;
import reactor.core.publisher.Mono;

/**
 * LuzDelSurService interface defines the methods for interacting with the Luz del Sur API.
 * It includes methods for user login, retrieving account statements, fetching PDF documents,
 * and getting the last billing information.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
public interface LuzDelSurService {
    Mono<LoginResponse> getLogin(LoginRequest login);
    Mono<LightAccountStatementResponse> getAccountStatement(String supply);
    Mono<byte[]> getPdf(TicketRequest ticket);
    Mono<BillingResponse> getLastBilling(BillingRequest billingRequest);
}
