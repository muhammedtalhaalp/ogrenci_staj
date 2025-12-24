package org.example.controller;

import org.example.entity.Cv;
import org.example.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cvler")
public class CvController {

    private final CvService cvService;

    @Autowired
    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    @GetMapping
    public ResponseEntity<List<Cv>> tumCvleriGetir() {
        return new ResponseEntity<>(cvService.tumCvleriGetir(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cv> idIleCvGetir(@PathVariable Long id) {
        return cvService.idIleCvGetir(id)
                .map(cv -> new ResponseEntity<>(cv, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cv> cvEkle(@RequestBody Cv cv) {
        return new ResponseEntity<>(cvService.cvKaydet(cv), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cv> cvGuncelle(@PathVariable Long id, @RequestBody Cv cv) {
        return cvService.idIleCvGetir(id)
                .map(mevcutCv -> {
                    cv.setId(id);
                    return new ResponseEntity<>(cvService.cvKaydet(cv), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cvSil(@PathVariable Long id) {
        cvService.cvSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
