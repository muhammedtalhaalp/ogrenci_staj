package org.example.controller;

import org.example.entity.StajIlani;
import org.example.service.StajIlaniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staj-ilanlari")
public class StajIlaniController {

    private final StajIlaniService stajIlaniService;

    @Autowired
    public StajIlaniController(StajIlaniService stajIlaniService) {
        this.stajIlaniService = stajIlaniService;
    }

    @GetMapping
    public ResponseEntity<List<StajIlani>> tumStajIlanlariniGetir() {
        return new ResponseEntity<>(stajIlaniService.tumStajIlanlariniGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StajIlani> idIleStajIlaniGetir(@PathVariable Long id) {
        return stajIlaniService.idIleStajIlaniGetir(id)
                .map(stajIlani -> new ResponseEntity<>(stajIlani, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StajIlani> stajIlaniEkle(@RequestBody StajIlani stajIlani) {
        return new ResponseEntity<>(stajIlaniService.stajIlaniKaydet(stajIlani), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StajIlani> stajIlaniGuncelle(@PathVariable Long id, @RequestBody StajIlani stajIlani) {
        return stajIlaniService.idIleStajIlaniGetir(id)
                .map(mevcutStajIlani -> {
                    stajIlani.setId(id);
                    return new ResponseEntity<>(stajIlaniService.stajIlaniKaydet(stajIlani), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> stajIlaniSil(@PathVariable Long id) {
        stajIlaniService.stajIlaniSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
