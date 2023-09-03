package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.BackOfficeRouteEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteConfigAngular {
    private String allowedRoute;
    private List<String> allowedCrud;
}
