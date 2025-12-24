package org.example.service;

import org.example.entity.Basvuru;
import org.example.repository.BasvuruRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasvuruService {

    private final BasvuruRepository basvuruRepository;

    @Autowired
    public BasvuruService(BasvuruRepository basvuruRepository) {
        this.basvuruRepository = basvuruRepository;
    }

    public List<Basvuru> tumBasvurulariGetir() {
        return basvuruRepository.findAll();
    }

    public Optional<Basvuru> idIleBasvuruGetir(Long id) {
        return basvuruRepository.findById(id);
    }

    public Basvuru basvuruKaydet(Basvuru basvuru) {
        return basvuruRepository.save(basvuru);
    }

    public void basvuruSil(Long id) {
        basvuruRepository.deleteById(id);
    }
}
