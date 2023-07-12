package com.digital.api.digital_booking.jwt;

import jakarta.servlet.http.HttpServletRequest;

public interface BearerTokenResolver {
    String resolve(HttpServletRequest request);
}