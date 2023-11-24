package binarfud.Challenge_5.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "orders")
public class Order extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed = false;
    private Integer totalprice;
    private Integer totalqty;

    @JsonBackReference
    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonBackReference
    @ManyToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
