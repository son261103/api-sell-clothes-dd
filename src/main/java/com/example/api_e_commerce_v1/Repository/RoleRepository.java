package com.example.api_e_commerce_v1.Repository;

import com.example.api_e_commerce_v1.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);

    boolean existsByName(String name);
}
