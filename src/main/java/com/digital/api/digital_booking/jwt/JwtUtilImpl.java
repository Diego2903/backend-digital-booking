package com.digital.api.digital_booking.jwt;

import com.digital.api.digital_booking.dto.UserDTO;
import com.digital.api.digital_booking.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtilImpl implements JwtUtil {

    @Override
    public String extractUserName(String token,String secretKey) {
        Claims claims = extractAllClaims(token,secretKey);
        return claims.getSubject();
    }

    @Override
    public String generateToken(UserDetails userDetails,
                                long systemCurrentMillis,
                                long configuredExpirationTimeInMillis,
                                String secretKey) {
        //get email from userDetails
        System.out.println("userDetails"+userDetails);
        String email = getEmailFromUserDetails(userDetails);
        System.out.println("email"+email);

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date(systemCurrentMillis))
                .setExpiration(new Date(systemCurrentMillis + configuredExpirationTimeInMillis))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }
    private String getEmailFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof UserDTO) {
            return ((UserDTO) userDetails).getEmail();
        }else if (userDetails instanceof Users) {
            return ((Users) userDetails).getEmail();
        }else {
            throw new IllegalArgumentException("UserDetails must be an instance of UserDTO or User");
        }
}

    @Override
    public Date extractExpiration(String token,String secretKey) {
        Claims claims = extractAllClaims(token,secretKey);
        return claims.getExpiration();
    }

    @Override
    public String extractEmail(String token, String secretKey) {
        Claims claims = extractAllClaims(token,secretKey);
        return claims.getSubject();

    }

    private Key getKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    private Claims extractAllClaims(String token,String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
