package org.example.controller;

import org.example.entity.Firma;
import org.example.service.FirmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firmalar")
public class FirmaController {

    private final FirmaService firmaService;

    @Autowired
    public FirmaController(FirmaService firmaService) {
        this.firmaService = firmaService;
    }

    @GetMapping
    public ResponseEntity<List<Firma>> tumFirmalariGetir() {
        return new ResponseEntity<>(firmaService.tumFirmalariGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Firma> idIleFirmaGetir(@PathVariable Long id) {
        return firmaService.idIleFirmaGetir(id)
                .map(firma -> new ResponseEntity<>(firma, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Firma> firmaEkle(@RequestBody Firma firma) {
        return new ResponseEntity<>(firmaService.firmaKaydet(firma), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Firma> firmaGuncelle(@PathVariable Long id, @RequestBody Firma firma) {
        return firmaService.idIleFirmaGetir(id)
                .map(mevcutFirma -> {
                    firma.setId(id);
                    return new ResponseEntity<>(firmaService.firmaKaydet(firma), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> firmaSil(@PathVariable Long id) {
        firmaService.firmaSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
