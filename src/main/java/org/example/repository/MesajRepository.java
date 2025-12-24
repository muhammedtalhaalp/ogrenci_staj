package org.example.repository;

import org.example.entity.Mesaj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesajRepository extends JpaRepository<Mesaj, Long> {
}
