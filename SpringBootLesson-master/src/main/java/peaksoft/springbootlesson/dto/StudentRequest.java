package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRequest {
    private String firsName;
    private String email;
    private String lastName;
    private String roleName;
    private String studyFormation;
    private String password;
    private Long groupId;


}
