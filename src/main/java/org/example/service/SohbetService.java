package org.example.service;

import org.example.entity.Sohbet;
import org.example.repository.SohbetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SohbetService {

    private final SohbetRepository sohbetRepository;

    @Autowired
    public SohbetService(SohbetRepository sohbetRepository) {
        this.sohbetRepository = sohbetRepository;
    }

    public List<Sohbet> tumSohbetleriGetir() {
        return sohbetRepository.findAll();
    }

    public Optional<Sohbet> idIleSohbetGetir(Long id) {
        return sohbetRepository.findById(id);
    }

    public Sohbet sohbetKaydet(Sohbet sohbet) {
        return sohbetRepository.save(sohbet);
    }

    public void sohbetSil(Long id) {
        sohbetRepository.deleteById(id);
    }
}
