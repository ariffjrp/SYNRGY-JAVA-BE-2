package binarfud.Challenge_5.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDetailDTO {

    private Integer quantity;
    private Integer priceItem;
    private String catatan;

}
