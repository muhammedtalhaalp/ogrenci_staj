package org.example.controller;

import org.example.entity.Basvuru;
import org.example.service.BasvuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basvurular")
public class BasvuruController {

    private final BasvuruService basvuruService;

    @Autowired
    public BasvuruController(BasvuruService basvuruService) {
        this.basvuruService = basvuruService;
    }

    @GetMapping
    public ResponseEntity<List<Basvuru>> tumBasvurulariGetir() {
        return new ResponseEntity<>(basvuruService.tumBasvurulariGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basvuru> idIleBasvuruGetir(@PathVariable Long id) {
        return basvuruService.idIleBasvuruGetir(id)
                .map(basvuru -> new ResponseEntity<>(basvuru, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Basvuru> basvuruEkle(@RequestBody Basvuru basvuru) {
        return new ResponseEntity<>(basvuruService.basvuruKaydet(basvuru), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Basvuru> basvuruGuncelle(@PathVariable Long id, @RequestBody Basvuru basvuru) {
        return basvuruService.idIleBasvuruGetir(id)
                .map(mevcutBasvuru -> {
                    basvuru.setId(id);
                    return new ResponseEntity<>(basvuruService.basvuruKaydet(basvuru), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> basvuruSil(@PathVariable Long id) {
        basvuruService.basvuruSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
