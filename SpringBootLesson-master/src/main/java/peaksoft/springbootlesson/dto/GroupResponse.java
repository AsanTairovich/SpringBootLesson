package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;
import peaksoft.springbootlesson.entity.Course;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GroupResponse {
    private Long id;
    private String groupName;
    private int dateOfStart;
    private int dateOfFinish;
    private LocalDate localDate;
    private List<Course> courseList;

}
