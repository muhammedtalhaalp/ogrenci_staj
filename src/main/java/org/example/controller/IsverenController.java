package org.example.controller;

import org.example.entity.Isveren;
import org.example.service.IsverenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/isverenler")
public class IsverenController {

    private final IsverenService isverenService;

    @Autowired
    public IsverenController(IsverenService isverenService) {
        this.isverenService = isverenService;
    }

    @GetMapping
    public ResponseEntity<List<Isveren>> tumIsverenleriGetir() {
        return new ResponseEntity<>(isverenService.tumIsverenleriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Isveren> idIleIsverenGetir(@PathVariable Long id) {
        return isverenService.idIleIsverenGetir(id)
                .map(isveren -> new ResponseEntity<>(isveren, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Isveren> isverenEkle(@RequestBody Isveren isveren) {
        return new ResponseEntity<>(isverenService.isverenKaydet(isveren), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Isveren> isverenGuncelle(@PathVariable Long id, @RequestBody Isveren isveren) {
        return isverenService.idIleIsverenGetir(id)
                .map(mevcutIsveren -> {
                    isveren.setId(id);
                    return new ResponseEntity<>(isverenService.isverenKaydet(isveren), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> isverenSil(@PathVariable Long id) {
        isverenService.isverenSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
