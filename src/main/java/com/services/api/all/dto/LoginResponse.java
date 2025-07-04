package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * LoginResponse class represents the response for a login operation.
 * It contains information about the success of the login, the token, and a message.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private boolean success;
    private String token;
    private String message;
}
