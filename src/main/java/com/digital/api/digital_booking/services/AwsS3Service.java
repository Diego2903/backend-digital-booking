package com.digital.api.digital_booking.services;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    String uploadFile(MultipartFile file,Long idProduct );
}
