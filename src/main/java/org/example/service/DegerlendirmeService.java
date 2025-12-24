package org.example.service;

import org.example.entity.Degerlendirme;
import org.example.repository.DegerlendirmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DegerlendirmeService {

    private final DegerlendirmeRepository degerlendirmeRepository;

    @Autowired
    public DegerlendirmeService(DegerlendirmeRepository degerlendirmeRepository) {
        this.degerlendirmeRepository = degerlendirmeRepository;
    }

    public List<Degerlendirme> tumDegerlendirmeleriGetir() {
        return degerlendirmeRepository.findAll();
    }

    public Optional<Degerlendirme> idIleDegerlendirmeGetir(Long id) {
        return degerlendirmeRepository.findById(id);
    }

    public Degerlendirme degerlendirmeKaydet(Degerlendirme degerlendirme) {
        return degerlendirmeRepository.save(degerlendirme);
    }

    public void degerlendirmeSil(Long id) {
        degerlendirmeRepository.deleteById(id);
    }
}
