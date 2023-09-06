package com.example.merchandise.database.repositories;

import com.example.merchandise.database.entities.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchandiseRepository extends JpaRepository<Merchandise, Long> {
    boolean existsByName(String name);
}
