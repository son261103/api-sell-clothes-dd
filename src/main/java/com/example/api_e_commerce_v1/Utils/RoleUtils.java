package com.example.api_e_commerce_v1.Utils;

import com.example.api_e_commerce_v1.Entity.Roles;
import com.example.api_e_commerce_v1.Enums.RoleType;

public class RoleUtils {
    public static Roles createAdminRole() {
        return Roles.builder()
                .roleId(1L)
                .name(RoleType.ROLE_ADMIN.name())
                .description("Administrator role with full system access")
                .build();
    }

    public static Roles createCustomerRole() {
        return Roles.builder()
                .roleId(2L)
                .name(RoleType.ROLE_CUSTOMER.name())
                .description("Customer role with limited access")
                .build();
    }
}