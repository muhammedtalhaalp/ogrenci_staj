package org.example.service;

import org.example.entity.StajIlani;
import org.example.repository.StajIlaniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StajIlaniService {

    private final StajIlaniRepository stajIlaniRepository;

    @Autowired
    public StajIlaniService(StajIlaniRepository stajIlaniRepository) {
        this.stajIlaniRepository = stajIlaniRepository;
    }

    public List<StajIlani> tumStajIlanlariniGetir() {
        return stajIlaniRepository.findAll();
    }

    public Optional<StajIlani> idIleStajIlaniGetir(Long id) {
        return stajIlaniRepository.findById(id);
    }

    public StajIlani stajIlaniKaydet(StajIlani stajIlani) {
        return stajIlaniRepository.save(stajIlani);
    }

    public void stajIlaniSil(Long id) {
        stajIlaniRepository.deleteById(id);
    }
}
