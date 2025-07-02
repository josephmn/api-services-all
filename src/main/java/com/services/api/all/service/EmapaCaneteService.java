package com.services.api.all.service;

import com.services.api.all.dto.WaterAccountStatementResponse;
import reactor.core.publisher.Flux;

public interface EmapaCaneteService {

    Flux<WaterAccountStatementResponse> getAccountStatement(String codSuc, String nroSuministro);
}
