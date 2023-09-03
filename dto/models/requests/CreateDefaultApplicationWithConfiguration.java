package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;

@Data
public class CreateDefaultApplicationWithConfiguration {
    private String name;
    private boolean autoSettle;
}
