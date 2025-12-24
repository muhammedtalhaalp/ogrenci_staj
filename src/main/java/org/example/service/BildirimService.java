package org.example.service;

import org.example.entity.Bildirim;
import org.example.repository.BildirimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BildirimService {

    private final BildirimRepository bildirimRepository;

    @Autowired
    public BildirimService(BildirimRepository bildirimRepository) {
        this.bildirimRepository = bildirimRepository;
    }

    public List<Bildirim> tumBildirimleriGetir() {
        return bildirimRepository.findAll();
    }

    public Optional<Bildirim> idIleBildirimGetir(Long id) {
        return bildirimRepository.findById(id);
    }

    public Bildirim bildirimKaydet(Bildirim bildirim) {
        return bildirimRepository.save(bildirim);
    }

    public void bildirimSil(Long id) {
        bildirimRepository.deleteById(id);
    }
}
