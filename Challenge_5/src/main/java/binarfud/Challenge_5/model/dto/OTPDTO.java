package binarfud.Challenge_5.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OTPDTO {
    private Integer code;
    private Boolean isVerified;

    public OTPDTO(Integer code, Boolean isVerified) {
        this.code = code;
        this.isVerified = isVerified;
    }
}
