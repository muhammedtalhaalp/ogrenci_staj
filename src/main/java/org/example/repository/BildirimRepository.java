package org.example.repository;

import org.example.entity.Bildirim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BildirimRepository extends JpaRepository<Bildirim, Long> {
}
