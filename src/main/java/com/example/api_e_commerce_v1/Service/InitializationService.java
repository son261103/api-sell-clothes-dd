package com.example.api_e_commerce_v1.Service;

import com.example.api_e_commerce_v1.Entity.Roles;
import com.example.api_e_commerce_v1.Entity.Users;
import com.example.api_e_commerce_v1.Enums.RoleType;
import com.example.api_e_commerce_v1.Enums.UserStatus;
import com.example.api_e_commerce_v1.Repository.RoleRepository;
import com.example.api_e_commerce_v1.Repository.UserRepository;
import com.example.api_e_commerce_v1.Utils.RoleUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitializationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        createDefaultRoles();
        createDefaultAdmin();
    }

    private void createDefaultRoles() {
        // Create ROLE_ADMIN if not exists
        if (!roleRepository.existsByName(RoleType.ROLE_ADMIN.name())) {
            Roles adminRole = RoleUtils.createAdminRole();
            adminRole.setCreatedAt(LocalDateTime.now());
            roleRepository.save(adminRole);
        }

        // Create ROLE_CUSTOMER if not exists
        if (!roleRepository.existsByName(RoleType.ROLE_CUSTOMER.name())) {
            Roles customerRole = RoleUtils.createCustomerRole();
            customerRole.setCreatedAt(LocalDateTime.now());
            roleRepository.save(customerRole);
        }
    }

    private void createDefaultAdmin() {
        if (!userRepository.existsByUsername("admin")) {
            // Get admin role
            Roles adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN.name())
                    .orElseThrow(() -> new RuntimeException("Admin role not found"));

            // Create admin user
            Users admin = Users.builder()
                    .userId(1L)
                    .username("admin")
                    .email("admin@system.com")
                    .passwordHash(passwordEncoder.encode("Admin@123"))
                    .fullName("System Administrator")
                    .status(UserStatus.ACTIVE)
                    .roles(Set.of(adminRole))
                    .createdAt(LocalDateTime.now())
                    .build();

            userRepository.save(admin);
        }
    }
}