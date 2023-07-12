package com.digital.api.digital_booking.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class AwsS3ServiceImpl implements AwsS3Service{
private static final Logger LOGGER = LoggerFactory.getLogger(AwsS3ServiceImpl.class);
    @Autowired
    private AmazonS3 s3client;
    @Autowired
    private ProductService productService;
    private final ResourceLoader resourceLoader;

    @Autowired
    public AwsS3ServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file,Long idProduct) {
        File mainFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            String fileName = System.currentTimeMillis() + "_" + Objects.requireNonNull(file.getOriginalFilename());
            LOGGER.info("Uploading file with name " + fileName);

            // Obtener la ruta del directorio "uploads"
            File uploadDir = new File("uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }




            // Crear el archivo en el directorio "uploads"
            Path filePath = uploadDir.toPath().resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Subir el archivo al bucket de S3
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, filePath.toFile());
            s3client.putObject(request);

            String url = "https://s3.amazonaws.com/s3buckets.c3.g5" + "/" + fileName;
            // Actualizar array de imágenes de producto
            productService.updateProductImage(idProduct, url);
            return url;

        } catch (IOException e) {
            LOGGER.error("Error while uploading file to S3 bucket", e);
            return "Error while uploading file to S3 bucket";
        }
    }
}
