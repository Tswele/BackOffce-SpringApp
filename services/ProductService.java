package za.co.wirecard.channel.backoffice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.MerchantProductData;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetApplicationProductRequest;
import za.co.wirecard.channel.backoffice.models.MerchantProduct;

import java.util.List;

@Service
public interface ProductService {

    ResponseEntity<MerchantProductData> getMerchantProducts(long merchantId);

    ResponseEntity<?> setMerchantProducts(long merchantId, MerchantProductData merchantProductData);

    ResponseEntity<?> getApplicationProducts(long applicationId);

    ResponseEntity<?> setApplicationProducts(long applicationId, long backOfficeUserId, List<SetApplicationProductRequest> setApplicationProductRequestList);

    MerchantProduct getMerchantProductStatus(long merchantId, long productId);
}
