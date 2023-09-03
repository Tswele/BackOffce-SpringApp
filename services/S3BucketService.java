package za.co.wirecard.channel.backoffice.services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.ApplicationConstants;
import za.co.wirecard.channel.backoffice.dto.models.s3.GetObjectUrl;
import za.co.wirecard.channel.backoffice.dto.models.s3.MultipartFileUnique;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;

import java.io.IOException;

@Service
public class S3BucketService {

    @Value("${aws.s3.bucketname}")
    private String bucketName;

    public GetObjectUrl uploadFileToS3Bucket(MultipartFileUnique multipartFileUnique) throws IOException {
        Regions clientRegion = Regions.AF_SOUTH_1;
        // LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "/" +
        String fileObjKeyName =  multipartFileUnique.getImage().getOriginalFilename();
        // File file = new File(putObjectS3Bucket.getMultipartFileUnique());

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

//        if (s3Client.doesObjectExist(bucketName, fileObjKeyName)) {
//            throw new GenericException("Existing object found | " + fileObjKeyName, HttpStatus.CONFLICT, "Object already exists with namespace | " + fileObjKeyName);
//        }
        // logger.info("Object upload started | " + file.getAbsolutePath());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(multipartFileUnique.getImage().getContentType());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileObjKeyName, multipartFileUnique.getImage().getInputStream(), metadata);
        PutObjectResult putObjectResult = s3Client.putObject(putObjectRequest
                .withCannedAcl(CannedAccessControlList.PublicRead));
        // Set the presigned URL to expire after one hour.
//        java.util.Date expiration = new java.util.Date();
//        long expTimeMillis = expiration.getTime();
//        expTimeMillis += 1000 * 60 * 60;
//        expiration.setTime(expTimeMillis);
//        GenerateUrl generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileObjKeyName)
//                .withMethod(HttpMethod.GET);
//                //.withExpiration(expiration);
        return new GetObjectUrl(s3Client.getUrl(bucketName, fileObjKeyName), fileObjKeyName);
    }

    public void deleteFileFromS3Bucket(String fileName) {
        Regions clientRegion = Regions.AF_SOUTH_1;

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(clientRegion)
                .build();

        s3Client.deleteObject(bucketName, fileName);
    }

}
