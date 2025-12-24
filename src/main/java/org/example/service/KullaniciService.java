package org.example.service;

import org.example.entity.Kullanici;
import org.example.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public KullaniciService(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Optional<Kullanici> idIleKullaniciGetir(Long id) {
        return kullaniciRepository.findById(id);
    }

    public Optional<Kullanici> emailIleKullaniciGetir(String email) {
        return kullaniciRepository.findByEmail(email);
    }

    public Kullanici kullaniciKaydet(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }

    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }
}
