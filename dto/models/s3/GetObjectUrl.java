package za.co.wirecard.channel.backoffice.dto.models.s3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectUrl {
    private URL url;
    private String name;
}
