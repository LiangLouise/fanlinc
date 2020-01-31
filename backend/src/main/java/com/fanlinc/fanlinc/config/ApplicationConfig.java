package com.fanlinc.fanlinc.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ApplicationConfig {
	
	@Value("${cloud.aws.credentials.accessKey}")
	String accessKey;
	
	@Value("${cloud.aws.credentials.secretKey}")
	String serectKey;
	
	@Value("${cloud.aws.credentials.endpoint}")
	String endpoint;
	
	@Value("${cloud.aws.region.static}") 
	String region;

    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKey, serectKey);
    }



    @Bean
    public AmazonS3 amazonS3Client(AWSCredentials credentials,
                                   String region) {

        AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials));
        builder.setEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region));
        return builder.build();
    }
}
