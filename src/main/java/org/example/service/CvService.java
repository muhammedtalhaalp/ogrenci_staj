package org.example.service;

import org.example.entity.Cv;
import org.example.repository.CvRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CvService {

    private final CvRepository cvRepository;

    @Autowired
    public CvService(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public List<Cv> tumCvleriGetir() {
        return cvRepository.findAll();
    }

    public Optional<Cv> idIleCvGetir(Long id) {
        return cvRepository.findById(id);
    }

    public Cv cvKaydet(Cv cv) {
        return cvRepository.save(cv);
    }

    public void cvSil(Long id) {
        cvRepository.deleteById(id);
    }
}
