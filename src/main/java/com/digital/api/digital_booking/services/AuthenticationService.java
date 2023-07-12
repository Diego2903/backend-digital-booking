package com.digital.api.digital_booking.services;


import com.digital.api.digital_booking.dto.AuthenticationResponse;
import com.digital.api.digital_booking.dto.LoginRequest;
import com.digital.api.digital_booking.dto.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse login(LoginRequest loginRequest);
    AuthenticationResponse signUp(SignUpRequest signUpRequest);

}
