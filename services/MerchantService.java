package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.MerchantDocument;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformUpdateMerchantRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.MerchantViewDetailResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateMerchantResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetMerchantByIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetMerchantsResponse;
import za.co.wirecard.channel.backoffice.dto.models.s3.MultipartFileUnique;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.exceptions.MerchantException;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;
import za.co.wirecard.channel.backoffice.models.User;

import java.util.List;

@Service
public interface MerchantService {

    Page<MerchantEntity> getMerchantsByFilter(int page, int limit, String stringCriteria, String stringSearch);

    Page<MerchantEntity> getOnboardingMerchantsByFilter(int page, int limit, String stringCriteria, String stringSearch);

    Page<PlatformGetMerchantsResponse> getMerchants(int page, int limit, String searchString) throws MerchantException;

    PlatformGetMerchantByIdResponse getMerchant(long id) throws MerchantException;

    PlatformCreateMerchantResponse createMerchant(PlatformCreateMerchantRequest merchant) throws MerchantException;

    void updateMerchant(long id, PlatformUpdateMerchantRequest merchant) throws MerchantException;

    void deleteMerchant(long id) throws MerchantException;

    List<MerchantClassification> getMerchantTypes() throws MerchantException;

    List<MerchantStatus> getMerchantStatuses() throws MerchantException;

    List<User> getAccountManagers() throws MerchantException;

    List<User> getSalesPersons() throws MerchantException;

    List<User> getCreditControllers() throws MerchantException;

    List<PlatformGetMerchantsResponse> getAllMerchants() throws MerchantException;;

    PlatformCreateMerchantResponse editMerchant(PlatformCreateMerchantRequest platformCreateMerchantRequest, long merchantId);

    MerchantViewDetailResponse getMerchantViewDetail(long merchantId);

    List<MerchantDocument> postMerchantDocuments(MultipartFileUnique multipartFileUnique, long merchantId);

    List<MerchantDocument> getMerchantDocuments(long id);

    List<MerchantDocument> deleteMerchantDocuments(long id, long merchantId);

//    void cancelMerchant(long merchantId, long userId);
}
