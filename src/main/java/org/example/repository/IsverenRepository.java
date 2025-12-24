package org.example.repository;

import org.example.entity.Isveren;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsverenRepository extends JpaRepository<Isveren, Long> {
}
