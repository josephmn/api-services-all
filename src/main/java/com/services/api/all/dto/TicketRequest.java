package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * TicketRequest class represents a request for a ticket.
 * It contains the necessary fields to identify the ticket.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class TicketRequest {

    private String suministro;
    private String correlativo;
}
