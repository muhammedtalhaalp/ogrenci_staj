package org.example.controller;

import org.example.entity.Sohbet;
import org.example.service.SohbetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sohbetler")
public class SohbetController {

    private final SohbetService sohbetService;

    @Autowired
    public SohbetController(SohbetService sohbetService) {
        this.sohbetService = sohbetService;
    }

    @GetMapping
    public ResponseEntity<List<Sohbet>> tumSohbetleriGetir() {
        return new ResponseEntity<>(sohbetService.tumSohbetleriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sohbet> idIleSohbetGetir(@PathVariable Long id) {
        return sohbetService.idIleSohbetGetir(id)
                .map(sohbet -> new ResponseEntity<>(sohbet, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Sohbet> sohbetEkle(@RequestBody Sohbet sohbet) {
        return new ResponseEntity<>(sohbetService.sohbetKaydet(sohbet), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sohbet> sohbetGuncelle(@PathVariable Long id, @RequestBody Sohbet sohbet) {
        return sohbetService.idIleSohbetGetir(id)
                .map(mevcutSohbet -> {
                    sohbet.setId(id);
                    return new ResponseEntity<>(sohbetService.sohbetKaydet(sohbet), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sohbetSil(@PathVariable Long id) {
        sohbetService.sohbetSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
