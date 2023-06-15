package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootlesson.entity.Group;

import java.time.LocalDate;

@Getter
@Setter
public class StudentResponse {
    private Long id;
    private String firsName;
    private String email;
    private String lastName;
    private String roleName;
    private String studyFormation;
    private LocalDate localDate;
    private Group group;
}
