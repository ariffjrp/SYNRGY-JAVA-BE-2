package binarfud.Challenge_5.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {
    private List<String> recipients;
    private String subject;
    private String content;

    public EmailDTO(List<String> recipients, String subject, String content) {
        this.recipients = recipients;
        this.subject = subject;
        this.content = content;
    }
}
