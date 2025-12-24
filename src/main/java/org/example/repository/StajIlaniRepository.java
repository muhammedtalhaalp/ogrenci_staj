package org.example.repository;

import org.example.entity.StajIlani;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StajIlaniRepository extends JpaRepository<StajIlani, Long> {
}
