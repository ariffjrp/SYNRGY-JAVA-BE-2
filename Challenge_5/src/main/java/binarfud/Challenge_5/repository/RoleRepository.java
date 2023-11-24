package binarfud.Challenge_5.repository;

import binarfud.Challenge_5.model.ERole;
import binarfud.Challenge_5.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByRole(ERole selectedRole);
}
