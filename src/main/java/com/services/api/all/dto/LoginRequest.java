package com.services.api.all.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * LoginRequest class represents the request body for user login.
 * It contains the user's email and password.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    private String correo;
    private String contrasenia;
}
