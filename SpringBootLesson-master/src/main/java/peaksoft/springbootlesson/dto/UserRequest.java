package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String roleName;

}
