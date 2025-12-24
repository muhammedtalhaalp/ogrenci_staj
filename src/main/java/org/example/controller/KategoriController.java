package org.example.controller;

import org.example.entity.Kategori;
import org.example.service.KategoriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kategoriler")
public class KategoriController {

    private final KategoriService kategoriService;

    @Autowired
    public KategoriController(KategoriService kategoriService) {
        this.kategoriService = kategoriService;
    }

    @GetMapping
    public ResponseEntity<List<Kategori>> tumKategorileriGetir() {
        return new ResponseEntity<>(kategoriService.tumKategorileriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kategori> idIleKategoriGetir(@PathVariable Long id) {
        return kategoriService.idIleKategoriGetir(id)
                .map(kategori -> new ResponseEntity<>(kategori, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Kategori> kategoriEkle(@RequestBody Kategori kategori) {
        return new ResponseEntity<>(kategoriService.kategoriKaydet(kategori), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kategori> kategoriGuncelle(@PathVariable Long id, @RequestBody Kategori kategori) {
        return kategoriService.idIleKategoriGetir(id)
                .map(mevcutKategori -> {
                    kategori.setId(id);
                    return new ResponseEntity<>(kategoriService.kategoriKaydet(kategori), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kategoriSil(@PathVariable Long id) {
        kategoriService.kategoriSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
