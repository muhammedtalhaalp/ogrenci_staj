package org.example.repository;

import org.example.entity.Sohbet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SohbetRepository extends JpaRepository<Sohbet, Long> {
}
