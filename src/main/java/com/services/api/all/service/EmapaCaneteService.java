package com.services.api.all.service;

import com.services.api.all.dto.WaterAccountStatementResponse;
import reactor.core.publisher.Flux;

/**
 * EmapaCaneteService interface defines the contract for services related to Emapa Canete.
 * It provides methods to retrieve water account statements based on supply codes.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
public interface EmapaCaneteService {

    Flux<WaterAccountStatementResponse> getAccountStatement(String codSuc, String nroSuministro);
}
