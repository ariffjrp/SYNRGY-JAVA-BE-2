package binarfud.Challenge_5.repository;

import binarfud.Challenge_5.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    @Procedure("CreateUserAccount")
    void createUserAccount(
            @Param("p_username") String username,
            @Param("p_email_address") String emailAddress,
            @Param("p_password") String password
    );

    @Procedure("updateUser")
    void updateUser(
            @Param("p_user_id") UUID id,
            @Param("p_username") String username,
            @Param("p_email_address") String emailAddress,
            @Param("p_password") String password
    );
}