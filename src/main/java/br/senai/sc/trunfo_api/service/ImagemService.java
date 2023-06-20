package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Imagem;
import br.senai.sc.trunfo_api.repository.ImagemRepository;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.pi.model.InvalidArgumentException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImagemService {
    private final ImagemRepository imagemRepository;
    private static String accessKey;
    private static String secretKey;
    @Value("${access_key}")
    public void setAccessKey(String accessKey) {
        ImagemService.accessKey = accessKey;
    }

    @Value("${secret_key}")
    public void setSecretKey(String secretKey) {
        ImagemService.secretKey = secretKey;
    }

    public URL findOne(Imagem imagem){
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();
            if (amazonS3Client.doesBucketExist("bucket-romario")) {
                return amazonS3Client.generatePresignedUrl("bucket-romario",
                        imagem.getUuid(), DateTime
                                .now()
                                .plusDays(1)
                                .toDate()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException();
    }

    public Imagem findOne(String uuid){
        return imagemRepository.findById(uuid).orElseThrow();
    }

    public List<URL> findAll() {
        List<URL> lista = new ArrayList<>();
        for (Imagem imagem : imagemRepository.findAll()) {
            lista.add(findOne(imagem));
        }
        return lista;
    }

    public Imagem save(Imagem imagem, String bucketName, File file) {
        try {
            BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3Client s3client = (AmazonS3Client) AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .build();

            s3client.putObject(new PutObjectRequest(bucketName, imagem.getUuid(), file));
            file.delete();
            return imagemRepository.save(imagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new InvalidArgumentException("Imagem não encontrada");
    }

    public void delete(String bucketName, String id) {
        try {
            Imagem imagem = imagemRepository.findById(id).orElse(null);
            if (imagem != null) {
                BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
                AmazonS3Client s3client = (AmazonS3Client) AmazonS3ClientBuilder
                        .standard()
                        .withRegion(Regions.US_EAST_1)
                        .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                        .build();
                s3client.deleteObject(new DeleteObjectRequest(bucketName, imagem.getUuid()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        imagemRepository.deleteById(id);
    }
}
