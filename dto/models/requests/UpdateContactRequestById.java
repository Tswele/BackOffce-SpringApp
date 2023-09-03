package za.co.wirecard.channel.backoffice.dto.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateContactRequestById {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cell;
    private String landline;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String position;

}
