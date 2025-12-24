package org.example.repository;

import org.example.entity.Degerlendirme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DegerlendirmeRepository extends JpaRepository<Degerlendirme, Long> {
}
