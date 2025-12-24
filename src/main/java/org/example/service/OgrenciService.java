package org.example.service;

import org.example.entity.Ogrenci;
import org.example.repository.OgrenciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OgrenciService {

    private final OgrenciRepository ogrenciRepository;

    @Autowired
    public OgrenciService(OgrenciRepository ogrenciRepository) {
        this.ogrenciRepository = ogrenciRepository;
    }

    public List<Ogrenci> tumOgrencileriGetir() {
        return ogrenciRepository.findAll();
    }

    public Optional<Ogrenci> idIleOgrenciGetir(Long id) {
        return ogrenciRepository.findById(id);
    }

    public Ogrenci ogrenciKaydet(Ogrenci ogrenci) {
        return ogrenciRepository.save(ogrenci);
    }

    public void ogrenciSil(Long id) {
        ogrenciRepository.deleteById(id);
    }
}
