package br.senai.sc.trunfo_api.controller;

import br.senai.sc.trunfo_api.model.DTO.ImagemDTO;
import br.senai.sc.trunfo_api.model.Entity.Imagem;
import br.senai.sc.trunfo_api.service.ImagemService;
import com.amazonaws.services.xray.model.Http;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@CrossOrigin
@Controller
@AllArgsConstructor
@RequestMapping("/img")
public class ImageController {
    private final ImagemService imagemService;

    @GetMapping
    public ResponseEntity<List<URL>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(imagemService.findAll());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<URL> findOne(@PathVariable String uuid){
        return ResponseEntity.status(HttpStatus.OK).body(imagemService.findOne(imagemService.findOne(uuid)));
    }

    @PostMapping("/mpf")
    public ResponseEntity<Imagem> create(@RequestParam(name = "img") MultipartFile multipartFile) {
        Imagem imagem = new Imagem();
        imagem.setUuid(UUID.randomUUID().toString());
        ImagemDTO imagemDTO = new ImagemDTO(imagem.getUuid(), multipartFile.getOriginalFilename());
        BeanUtils.copyProperties(imagemDTO, imagem);
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(imagemService.save(imagem, "bucket-romario", file));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        imagemService.delete("bucket-romario", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Objeto deletado");
    }
}
