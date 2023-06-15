package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootlesson.entity.Course;

import java.time.LocalDate;

@Getter
@Setter
public class TeacherResponse {
    private Long id;
    private String firsName;
    private String email;
    private String lastName;
    private String roleName;
    private LocalDate localDate;
    private Course course;

}
