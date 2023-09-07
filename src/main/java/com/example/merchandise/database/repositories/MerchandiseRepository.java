package com.example.merchandise.database.repositories;

import com.example.merchandise.database.entities.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByName(String name);
    List<Merchandise> findByNameContainingIgnoreCase(String name);
}
