package org.example.service;

import org.example.entity.Kategori;
import org.example.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    @Autowired
    public KategoriService(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<Kategori> tumKategorileriGetir() {
        return kategoriRepository.findAll();
    }

    public Optional<Kategori> idIleKategoriGetir(Long id) {
        return kategoriRepository.findById(id);
    }

    public Kategori kategoriKaydet(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    public void kategoriSil(Long id) {
        kategoriRepository.deleteById(id);
    }
}
