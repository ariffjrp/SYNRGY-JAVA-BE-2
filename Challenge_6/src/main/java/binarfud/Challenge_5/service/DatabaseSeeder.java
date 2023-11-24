package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.ERole;
import binarfud.Challenge_5.model.Role;
import binarfud.Challenge_5.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        seedRoles();
    }

    private void seedRoles() {
        Arrays.stream(ERole.values())
                .forEach(role -> {
                    Role existingRole = roleRepository.findByRole(role);
                    if (existingRole == null) {
                        roleRepository.save(new Role(role));
                    }
                });
    }
}
