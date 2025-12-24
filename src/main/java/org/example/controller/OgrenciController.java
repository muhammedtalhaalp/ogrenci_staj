package org.example.controller;

import org.example.entity.Ogrenci;
import org.example.service.OgrenciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ogrenciler")
public class OgrenciController {

    private final OgrenciService ogrenciService;

    @Autowired
    public OgrenciController(OgrenciService ogrenciService) {
        this.ogrenciService = ogrenciService;
    }

    @GetMapping
    public ResponseEntity<List<Ogrenci>> tumOgrencileriGetir() {
        return new ResponseEntity<>(ogrenciService.tumOgrencileriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ogrenci> idIleOgrenciGetir(@PathVariable Long id) {
        return ogrenciService.idIleOgrenciGetir(id)
                .map(ogrenci -> new ResponseEntity<>(ogrenci, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Ogrenci> ogrenciEkle(@RequestBody Ogrenci ogrenci) {
        return new ResponseEntity<>(ogrenciService.ogrenciKaydet(ogrenci), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ogrenci> ogrenciGuncelle(@PathVariable Long id, @RequestBody Ogrenci ogrenci) {
        return ogrenciService.idIleOgrenciGetir(id)
                .map(mevcutOgrenci -> {
                    ogrenci.setId(id);
                    return new ResponseEntity<>(ogrenciService.ogrenciKaydet(ogrenci), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> ogrenciSil(@PathVariable Long id) {
        ogrenciService.ogrenciSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
