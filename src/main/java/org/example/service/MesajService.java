package org.example.service;

import org.example.entity.Mesaj;
import org.example.repository.MesajRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesajService {

    private final MesajRepository mesajRepository;

    @Autowired
    public MesajService(MesajRepository mesajRepository) {
        this.mesajRepository = mesajRepository;
    }

    public List<Mesaj> tumMesajlariGetir() {
        return mesajRepository.findAll();
    }

    public Optional<Mesaj> idIleMesajGetir(Long id) {
        return mesajRepository.findById(id);
    }

    public Mesaj mesajKaydet(Mesaj mesaj) {
        return mesajRepository.save(mesaj);
    }

    public void mesajSil(Long id) {
        mesajRepository.deleteById(id);
    }
}
