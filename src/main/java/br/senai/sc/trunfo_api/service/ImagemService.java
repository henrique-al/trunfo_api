package br.senai.sc.trunfo_api.service;

import br.senai.sc.trunfo_api.model.Entity.Imagem;
import br.senai.sc.trunfo_api.model.utils.AmazonS3BuilderUtils;
import br.senai.sc.trunfo_api.repository.ImagemRepository;
import com.amazonaws.services.pi.model.InvalidArgumentException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImagemService {
    private final ImagemRepository imagemRepository;

    public URL findOne(Imagem imagem) {
        try {
            AmazonS3Client amazonS3Client = new AmazonS3BuilderUtils().criarClient();
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

    public Imagem findOne(String uuid) {
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
            AmazonS3Client amazonS3Client = new AmazonS3BuilderUtils().criarClient();
            amazonS3Client.putObject(new PutObjectRequest(bucketName, imagem.getUuid(), file));
            file.delete();
            return imagemRepository.save(imagem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new InvalidArgumentException("Imagem não encontrada");
    }

    public void delete(String bucketName, String id) {
        try {
            Imagem imagem = imagemRepository.findById(id).orElseThrow();
            AmazonS3Client amazonS3Client = new AmazonS3BuilderUtils().criarClient();
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, imagem.getUuid()));
            imagemRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
