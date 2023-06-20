package br.senai.sc.trunfo_api.model.utils;

import br.senai.sc.trunfo_api.service.ImagemService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

public class AmazonS3BuilderUtils {

    @Value("${access_key}")
    private static String accessKey;
    @Value("${secret_key}")
    private static String secretKey;

    public static AmazonS3Client criarClient(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
