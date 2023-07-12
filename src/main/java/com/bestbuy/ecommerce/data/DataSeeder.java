package com.bestbuy.ecommerce.data;

import com.bestbuy.ecommerce.utils.Permission;
import com.bestbuy.ecommerce.domain.entity.Role;
import com.bestbuy.ecommerce.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role();
        adminRole.setRoleName("ADMIN");
        adminRole.setPermissions(
                List.of(
                        Permission.ADMIN_CREATE,
                        Permission.ADMIN_READ,
                        Permission.ADMIN_UPDATE,
                        Permission.ADMIN_DELETE,
                        Permission.CUSTOMER_CREATE,
                        Permission.CUSTOMER_READ,
                        Permission.CUSTOMER_UPDATE,
                        Permission.CUSTOMER_DELETE
                ));

        Role customerRole = new Role();
        adminRole.setRoleName("CUSTOMER");
        customerRole.setPermissions(
                List.of(
                        Permission.CUSTOMER_CREATE,
                        Permission.CUSTOMER_READ,
                        Permission.CUSTOMER_UPDATE,
                        Permission.CUSTOMER_DELETE
                ));

        roleRepository.save(adminRole);
        roleRepository.save(customerRole);
    }
}