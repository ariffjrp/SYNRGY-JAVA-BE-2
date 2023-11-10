package binarfud.Challenge_4.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private String name;
    private String address;
    private String phone;
    private Integer saldo;

    public AccountDTO(String name, String address, String phone, Integer saldo) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.saldo = saldo;
    }
}
