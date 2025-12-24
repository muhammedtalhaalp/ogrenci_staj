package org.example.controller;

import org.example.entity.Degerlendirme;
import org.example.service.DegerlendirmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/degerlendirmeler")
public class DegerlendirmeController {

    private final DegerlendirmeService degerlendirmeService;

    @Autowired
    public DegerlendirmeController(DegerlendirmeService degerlendirmeService) {
        this.degerlendirmeService = degerlendirmeService;
    }

    @GetMapping
    public ResponseEntity<List<Degerlendirme>> tumDegerlendirmeleriGetir() {
        return new ResponseEntity<>(degerlendirmeService.tumDegerlendirmeleriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Degerlendirme> idIleDegerlendirmeGetir(@PathVariable Long id) {
        return degerlendirmeService.idIleDegerlendirmeGetir(id)
                .map(degerlendirme -> new ResponseEntity<>(degerlendirme, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Degerlendirme> degerlendirmeEkle(@RequestBody Degerlendirme degerlendirme) {
        return new ResponseEntity<>(degerlendirmeService.degerlendirmeKaydet(degerlendirme), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Degerlendirme> degerlendirmeGuncelle(@PathVariable Long id, @RequestBody Degerlendirme degerlendirme) {
        return degerlendirmeService.idIleDegerlendirmeGetir(id)
                .map(mevcutDegerlendirme -> {
                    degerlendirme.setId(id);
                    return new ResponseEntity<>(degerlendirmeService.degerlendirmeKaydet(degerlendirme), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> degerlendirmeSil(@PathVariable Long id) {
        degerlendirmeService.degerlendirmeSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
