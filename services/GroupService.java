package za.co.wirecard.channel.backoffice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.BackOfficeDefaultGroup;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateGroupRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformUpdateGroupRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformGetGroupByIdResponse;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeGroupPermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.exceptions.GroupException;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeGroupPermissionRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeGroupRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficePermissionRepository;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.common.exceptions.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private final BackOfficeGroupRepository backOfficeGroupRepository;
    private final BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository;
    private final BackOfficePermissionRepository backOfficePermissionRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public GroupService(BackOfficeGroupRepository backOfficeGroupRepository, BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository, BackOfficePermissionRepository backOfficePermissionRepository, BackOfficeUserRepository backOfficeUserRepository) {
        this.backOfficeGroupRepository = backOfficeGroupRepository;
        this.backOfficeGroupPermissionRepository = backOfficeGroupPermissionRepository;
        this.backOfficePermissionRepository = backOfficePermissionRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
    }

    public Page<PlatformGetGroupByIdResponse> getGroups(int page, int limit, HttpServletRequest httpServletRequest) throws GroupException {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<BackOfficeGroupEntity> backOfficeGroupEntities = backOfficeGroupRepository.findAll(pageable);
        List<PlatformGetGroupByIdResponse> platformGetGroupByIdResponses = backOfficeGroupEntities
                .stream()
                .map(backOfficeGroupEntity -> new PlatformGetGroupByIdResponse(backOfficeGroupEntity, backOfficeGroupPermissionRepository))
                .collect(Collectors.toList());
//        final int start = (int)pageable.getOffset();
//        final int end = (page+1) * limit;
        Page<PlatformGetGroupByIdResponse> platformGetGroupByIdResponsesPage = new PageImpl<>(platformGetGroupByIdResponses, pageable, backOfficeGroupEntities.getTotalElements());
        return platformGetGroupByIdResponsesPage;
    }

    public List<PlatformGetGroupByIdResponse> getAllGroups(HttpServletRequest httpServletRequest) {
        List<BackOfficeGroupEntity> backOfficeGroupEntities = backOfficeGroupRepository.findAll();
        List<PlatformGetGroupByIdResponse> platformGetGroupByIdResponses = backOfficeGroupEntities
                .stream()
                .map(backOfficeGroupEntity -> new PlatformGetGroupByIdResponse(backOfficeGroupEntity, backOfficeGroupPermissionRepository))
                .collect(Collectors.toList());
        return platformGetGroupByIdResponses;
    }

    public PlatformGetGroupByIdResponse getGroup(long groupId) throws GroupException {
        BackOfficeGroupEntity backOfficeGroupEntity = backOfficeGroupRepository.findById(groupId).orElseThrow(() -> new UserNotFoundException(groupId));
        return new PlatformGetGroupByIdResponse(backOfficeGroupEntity, backOfficeGroupPermissionRepository);
    }

    public void createGroup(PlatformCreateGroupRequest group, HttpServletRequest servletRequest) throws GroupException {
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        // Decode JWT Token from accessToken
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        // Get user
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne(userId);
        BackOfficeGroupEntity backOfficeGroupEntity = new BackOfficeGroupEntity(group, backOfficeUserEntity);
        backOfficeGroupRepository.save(backOfficeGroupEntity);
        List<BackOfficePermissionEntity> backOfficePermissionEntityList = group.getPermissions()
                .stream()
                .map(aLong -> backOfficePermissionRepository.findById(aLong).orElseThrow(() -> new UserNotFoundException(aLong)))
                .collect(Collectors.toList());
        List<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionEntities = backOfficePermissionEntityList
                .stream()
                .map(backOfficePermissionEntity -> new BackOfficeGroupPermissionEntity(backOfficeGroupEntity, backOfficePermissionEntity))
                .collect(Collectors.toList());
        backOfficeGroupPermissionRepository.saveAll(backOfficeGroupPermissionEntities);
    }

    public void createDefaultAdminGroup(BackOfficeDefaultGroup group) throws GroupException {

    }

//    public PlatformCreateGroupResponse createMerchantDefaultAdminGroup(BackOfficeDefaultGroup group, HttpServletRequest httpServletRequest) throws GroupException {
//
//    }

    @Transactional
    public void updateGroup(long id, PlatformUpdateGroupRequest group, HttpServletRequest servletRequest) throws GroupException {
        // Group
        BackOfficeGroupEntity backOfficeGroupEntity = backOfficeGroupRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        // token
        String accessToken = servletRequest.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        if (accessToken == null) {
            throw new GenericException("Token information service was not passed proper parameters", HttpStatus.BAD_REQUEST, "Token derived object is null");
        }
        // Decode JWT Token from accessToken
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(accessToken);
        Long userId = decodedJWT.getClaims().get("userId") != null ? decodedJWT.getClaims().get("userId").asLong() : null;
        BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        //
        backOfficeGroupEntity.setName(group.getName());
        backOfficeGroupEntity.setBackOfficeUserByModifiedBy(backOfficeUserEntity);
        backOfficeGroupEntity.setCode(group.getCode());
        backOfficeGroupEntity.setCreatedDate(new Timestamp(Instant.now().toEpochMilli()));
        backOfficeGroupEntity.setLastModified(new Timestamp(Instant.now().toEpochMilli()));
        backOfficeGroupRepository.save(backOfficeGroupEntity);
        // Group permissions
        List<BackOfficePermissionEntity> backOfficePermissionEntityList = group.getPermissions()
                .stream()
                .map(aLong -> backOfficePermissionRepository.findById(aLong).orElseThrow(() -> new UserNotFoundException(id)))
                .collect(Collectors.toList());
        List<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionEntities = backOfficePermissionEntityList
                .stream()
                .map(backOfficePermissionEntity -> new BackOfficeGroupPermissionEntity(backOfficeGroupEntity, backOfficePermissionEntity))
                .collect(Collectors.toList());
        backOfficeGroupPermissionRepository.deleteAllByBackOfficeGroupByBackOfficeGroupId(backOfficeGroupEntity);
        backOfficeGroupPermissionRepository.saveAll(backOfficeGroupPermissionEntities);
    }

    @Transactional
    public void deleteGroup(long id) throws GroupException {
        BackOfficeGroupEntity backOfficeGroupEntity = backOfficeGroupRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        backOfficeGroupPermissionRepository.deleteAllByBackOfficeGroupByBackOfficeGroupId(backOfficeGroupEntity);
        backOfficeGroupRepository.delete(backOfficeGroupEntity);
    }

}
