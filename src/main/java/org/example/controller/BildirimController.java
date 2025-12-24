package org.example.controller;

import org.example.entity.Bildirim;
import org.example.service.BildirimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bildirimler")
public class BildirimController {

    private final BildirimService bildirimService;

    @Autowired
    public BildirimController(BildirimService bildirimService) {
        this.bildirimService = bildirimService;
    }

    @GetMapping
    public ResponseEntity<List<Bildirim>> tumBildirimleriGetir() {
        return new ResponseEntity<>(bildirimService.tumBildirimleriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bildirim> idIleBildirimGetir(@PathVariable Long id) {
        return bildirimService.idIleBildirimGetir(id)
                .map(bildirim -> new ResponseEntity<>(bildirim, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Bildirim> bildirimEkle(@RequestBody Bildirim bildirim) {
        return new ResponseEntity<>(bildirimService.bildirimKaydet(bildirim), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bildirim> bildirimGuncelle(@PathVariable Long id, @RequestBody Bildirim bildirim) {
        return bildirimService.idIleBildirimGetir(id)
                .map(mevcutBildirim -> {
                    bildirim.setId(id);
                    return new ResponseEntity<>(bildirimService.bildirimKaydet(bildirim), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> bildirimSil(@PathVariable Long id) {
        bildirimService.bildirimSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
