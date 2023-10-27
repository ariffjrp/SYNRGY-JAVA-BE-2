package binarfud.Challenge_4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "Users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;
    private String emailAddress;
    private String password;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Account account;
}
