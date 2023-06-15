package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRequest {
    private String firsName;
    private String email;
    private String LsatName;
    private String roleName;
    private String password;
    private Long courseId;

}
