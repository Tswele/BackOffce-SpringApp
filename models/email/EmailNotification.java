package za.co.wirecard.channel.backoffice.models.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification {
    private String toAdress;
    private String subject;
    private String body;
}
