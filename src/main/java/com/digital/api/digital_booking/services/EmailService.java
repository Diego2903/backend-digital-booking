package com.digital.api.digital_booking.services;

public interface EmailService {
    void sendMail(String to, String subject, String body);
}
