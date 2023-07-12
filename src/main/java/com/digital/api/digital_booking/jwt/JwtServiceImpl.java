package com.digital.api.digital_booking.jwt;

import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.models.Users;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final Clock clock;

    @Override
    public String extractUserName(String token) {
        return jwtUtil.extractUserName(token, jwtConfig.secretKey());
    }

    @Override
    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token, jwtConfig.secretKey());
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        System.out.println("userDetails"+ userDetails);
        return jwtUtil.generateToken(userDetails,
                clock.millis(),
                jwtConfig.expiration(),
                jwtConfig.secretKey());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, String username) {
String email = getEmailFromUserDetails(userDetails);
        return (username.equals(email)) && !isTokenExpired(token);
    }
    private String getEmailFromUserDetails(UserDetails userDetails) {
        System.out.println("userDetails class: " + userDetails.getClass().getName());

        if (userDetails instanceof UserDTO) {
            return ((UserDTO) userDetails).getEmail();
        } else if (userDetails instanceof Users) {
            // Si UserDetails es una instancia de User, realizar la conversión correspondiente
            return ((Users) userDetails).getEmail();
        } else {
            throw new IllegalArgumentException("UserDetails must be an instance of UserDTO or User");
        }
    }
    private boolean isTokenExpired(String token) {
        return jwtUtil.extractExpiration(token, jwtConfig.secretKey()).before(new Date(clock.millis()));
    }

}
