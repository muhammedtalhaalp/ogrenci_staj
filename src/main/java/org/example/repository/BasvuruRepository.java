package org.example.repository;

import org.example.entity.Basvuru;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasvuruRepository extends JpaRepository<Basvuru, Long> {
}
