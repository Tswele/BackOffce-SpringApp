package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class Provinces extends Province {
    private List<Province> provinces;
}
