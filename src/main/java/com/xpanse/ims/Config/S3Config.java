package com.xpanse.ims.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
//
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(ProfileCredentialsProvider.create("Default"))
                    .region(Region.US_EAST_1)
                    .build();
        return s3Client;

    }
}

