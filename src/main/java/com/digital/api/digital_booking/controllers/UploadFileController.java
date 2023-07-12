package com.digital.api.digital_booking.controllers;

import com.digital.api.digital_booking.services.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class UploadFileController {
    @Autowired
    private AwsS3Service awsS3Service;

    @PostMapping("/upload/{idProduct}")
    public ResponseEntity<String> uploadFile(@PathVariable Long idProduct, @RequestPart(value = "file") MultipartFile file) {
        System.out.println("idProduct: " + idProduct);
        System.out.println("file: " + file);
        String url = awsS3Service.uploadFile(file, idProduct);
        return ResponseEntity.ok(url);


    }


}
