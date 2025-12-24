package org.example.controller;

import org.example.entity.Mesaj;
import org.example.service.MesajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesajlar")
public class MesajController {

    private final MesajService mesajService;

    @Autowired
    public MesajController(MesajService mesajService) {
        this.mesajService = mesajService;
    }

    @GetMapping
    public ResponseEntity<List<Mesaj>> tumMesajlariGetir() {
        return new ResponseEntity<>(mesajService.tumMesajlariGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesaj> idIleMesajGetir(@PathVariable Long id) {
        return mesajService.idIleMesajGetir(id)
                .map(mesaj -> new ResponseEntity<>(mesaj, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mesaj> mesajEkle(@RequestBody Mesaj mesaj) {
        return new ResponseEntity<>(mesajService.mesajKaydet(mesaj), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesaj> mesajGuncelle(@PathVariable Long id, @RequestBody Mesaj mesaj) {
        return mesajService.idIleMesajGetir(id)
                .map(mevcutMesaj -> {
                    mesaj.setId(id);
                    return new ResponseEntity<>(mesajService.mesajKaydet(mesaj), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> mesajSil(@PathVariable Long id) {
        mesajService.mesajSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
