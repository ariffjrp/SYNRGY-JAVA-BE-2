package binarfud.Challenge_4.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private String deskripsi;
    private long price;
    private long qty;
}