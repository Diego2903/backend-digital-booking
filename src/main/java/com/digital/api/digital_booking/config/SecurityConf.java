package com.digital.api.digital_booking.config;

import com.digital.api.digital_booking.jwt.JwtRequestFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
//@EnableMethodSecurity
public class SecurityConf {

    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
                                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/sign-up").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users")
                                .hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users/*")
                                .permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/users/*")
                                .hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/s3/upload/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/userState/*").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/category/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/category/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/category/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/category/*").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/product/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/product/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/product/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/product/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/city/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/city/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/city/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/city/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/location/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/location/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/location/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/location/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/booking/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/booking/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/booking/*/*").permitAll()
                                .requestMatchers(HttpMethod.GET, "/politics/*").permitAll()
                                .requestMatchers(HttpMethod.POST, "/politics/*").hasAnyAuthority( "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/favorites/*").hasAnyAuthority( "USER")
                                .requestMatchers(HttpMethod.GET, "/favorites/*").hasAnyAuthority( "USER")
                                .requestMatchers(HttpMethod.DELETE, "/favorites/delete/**").hasAnyAuthority( "USER")

                                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
//                                .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("ADMIN") // Cambia "ROLE_ADMIN" según tu rol de administrador
                                .anyRequest().authenticated())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
