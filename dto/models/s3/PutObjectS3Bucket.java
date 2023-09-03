package za.co.wirecard.channel.backoffice.dto.models.s3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutObjectS3Bucket {

    private String fileName;
    private MultipartFileUnique multipartFileUnique;

}
