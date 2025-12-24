package org.example.service;

import org.example.entity.Isveren;
import org.example.repository.IsverenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IsverenService {

    private final IsverenRepository isverenRepository;

    @Autowired
    public IsverenService(IsverenRepository isverenRepository) {
        this.isverenRepository = isverenRepository;
    }

    public List<Isveren> tumIsverenleriGetir() {
        return isverenRepository.findAll();
    }

    public Optional<Isveren> idIleIsverenGetir(Long id) {
        return isverenRepository.findById(id);
    }

    public Isveren isverenKaydet(Isveren isveren) {
        return isverenRepository.save(isveren);
    }

    public void isverenSil(Long id) {
        isverenRepository.deleteById(id);
    }
}
