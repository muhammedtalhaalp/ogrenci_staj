package org.example.controller;

import org.example.entity.Kullanici;
import org.example.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @Autowired
    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @GetMapping
    public ResponseEntity<List<Kullanici>> tumKullanicilariGetir() {
        return new ResponseEntity<>(kullaniciService.tumKullanicilariGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Kullanici> idIleKullaniciGetir(@PathVariable Long id) {
        return kullaniciService.idIleKullaniciGetir(id)
                .map(kullanici -> new ResponseEntity<>(kullanici, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Kullanici> kullaniciEkle(@RequestBody Kullanici kullanici) {
        return new ResponseEntity<>(kullaniciService.kullaniciKaydet(kullanici), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kullanici> kullaniciGuncelle(@PathVariable Long id, @RequestBody Kullanici kullanici) {
        return kullaniciService.idIleKullaniciGetir(id)
                .map(mevcutKullanici -> {
                    kullanici.setId(id);
                    return new ResponseEntity<>(kullaniciService.kullaniciKaydet(kullanici), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciService.kullaniciSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
