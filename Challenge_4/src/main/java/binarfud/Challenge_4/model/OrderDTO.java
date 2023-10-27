package binarfud.Challenge_4.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class OrderDTO {
    private LocalDateTime orderTime;
    private String destinationAddress;
    private boolean completed = false;
    private Integer totalprice;
    private Integer totalqty;
}
