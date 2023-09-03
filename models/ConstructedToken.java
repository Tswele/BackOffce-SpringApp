package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BackOfficePermissionEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;
// import za.co.wirecard.channel.backoffice.entities.BackOfficeRoutePermissionEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructedToken {

    private BackOfficePermissionEntity authority;
    private RouteConfig routeConfig;

    public ConstructedToken(RouteCrud routeCrud) {

    }


}
