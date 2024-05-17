package com.example.bookapprestapi.services;

import com.example.bookapprestapi.models.Role;
import com.example.bookapprestapi.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findByRoleName(String role) {
        Optional<Role> foundedRole = roleRepository.findByName(role);
        return foundedRole.orElse(null);
    }
}
