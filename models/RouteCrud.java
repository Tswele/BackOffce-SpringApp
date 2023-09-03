package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BackOfficeCrudPermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;
import za.co.wirecard.channel.backoffice.entities.CrudOperationsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteCrud {
    private BackOfficeRouteEntity backOfficeRouteEntity;
    private List<CrudOperationsEntity> crudOperationsEntity;

    public RouteCrud(BackOfficePermissionEntity backOfficePermissionEntity) {
        this.backOfficeRouteEntity = backOfficePermissionEntity.getBackOfficeRouteEntity();
        this.crudOperationsEntity = backOfficePermissionEntity.getBackOfficeCrudPermissionsById()
                .stream()
                .map(BackOfficeCrudPermissionEntity::getCrudOperationsByBackOfficeCrudId)
                .collect(Collectors.toList());
    }

}
