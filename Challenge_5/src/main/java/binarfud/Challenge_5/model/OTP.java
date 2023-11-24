package binarfud.Challenge_5.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "otp")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private Boolean isVerified;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
