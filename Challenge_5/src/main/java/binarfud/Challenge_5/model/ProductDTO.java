package binarfud.Challenge_5.model;

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

    public ProductDTO(String name, String deskripsi, long price, long qty) {
        this.name = name;
        this.deskripsi = deskripsi;
        this.price = price;
        this.qty = qty;
    }
}
