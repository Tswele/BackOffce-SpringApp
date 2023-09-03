package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;
import za.co.wirecard.channel.backoffice.entities.CrudOperationsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteConfig {
    private String allowedRoute;
    private List<String> allowedCrud = new ArrayList<>();

    public RouteConfig(RouteCrud routeCrud) {
        if (routeCrud != null) {
            this.allowedRoute = routeCrud.getBackOfficeRouteEntity().getCode();
            for (CrudOperationsEntity crudOperationsEntity: routeCrud.getCrudOperationsEntity()) {
                if (!allowedCrud.contains(crudOperationsEntity.getCode())) {
                    allowedCrud.add(crudOperationsEntity.getCode());
                }
            }
        }
    }
}
