package com.example.api_e_commerce_v1.Mapper;

import com.example.api_e_commerce_v1.DTO.Roles.RoleCreateDTO;
import com.example.api_e_commerce_v1.DTO.Roles.RoleResponseDTO;
import com.example.api_e_commerce_v1.DTO.Roles.RoleUpdateDTO;
import com.example.api_e_commerce_v1.Entity.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper implements EntityMapper<Roles, RoleResponseDTO> {

    @Override
    public Roles toEntity(RoleResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        return Roles.builder()
                .roleId(dto.getRoleId())
                .name(dto.getName())
                .description(dto.getDescription())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public RoleResponseDTO toDto(Roles entity) {
        if (entity == null) {
            return null;
        }

        return RoleResponseDTO.builder()
                .roleId(entity.getRoleId())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<Roles> toEntity(List<RoleResponseDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleResponseDTO> toDto(List<Roles> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Thêm phương thức cho CreateDTO
    public Roles toEntity(RoleCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        return Roles.builder()
                .name(createDTO.getName())
                .description(createDTO.getDescription())
                .build();
    }

    // Thêm phương thức cho UpdateDTO
    public void updateEntityFromDTO(RoleUpdateDTO updateDTO, Roles role) {
        if (updateDTO == null || role == null) {
            return;
        }

        if (updateDTO.getName() != null) {
            role.setName(updateDTO.getName());
        }
        if (updateDTO.getDescription() != null) {
            role.setDescription(updateDTO.getDescription());
        }
    }
}