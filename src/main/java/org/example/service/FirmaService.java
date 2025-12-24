package org.example.service;

import org.example.entity.Firma;
import org.example.repository.FirmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FirmaService {

    private final FirmaRepository firmaRepository;

    @Autowired
    public FirmaService(FirmaRepository firmaRepository) {
        this.firmaRepository = firmaRepository;
    }

    public List<Firma> tumFirmalariGetir() {
        return firmaRepository.findAll();
    }

    public Optional<Firma> idIleFirmaGetir(Long id) {
        return firmaRepository.findById(id);
    }

    public Firma firmaKaydet(Firma firma) {
        return firmaRepository.save(firma);
    }

    public void firmaSil(Long id) {
        firmaRepository.deleteById(id);
    }
}
