package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;
import za.co.wirecard.channel.backoffice.models.Permission;
import za.co.wirecard.channel.backoffice.repositories.BackOfficePermissionRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final BackOfficePermissionRepository backOfficePermissionRepository;

    public PermissionService(BackOfficePermissionRepository backOfficePermissionRepository) {
        this.backOfficePermissionRepository = backOfficePermissionRepository;
    }

    public List<Permission> getPermissions(HttpServletRequest servletRequest) {

        List<String> permissionList = new ArrayList<>();
        List<Permission> backOfficePermissionEntities = new ArrayList<>();

        backOfficePermissionRepository
                .findAll()
                .stream()
                .distinct()
                .forEach(backOfficePermissionEntity -> {
                    Permission permission = new Permission(backOfficePermissionEntity);
                    if (!permissionList.contains(permission.getCode())) {
                        backOfficePermissionEntities.add(permission);
                    }
                    permissionList.add(permission.getCode());
                });

        return backOfficePermissionEntities;
    }
}
