package peaksoft.springbootlesson.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupRequest {
    private String groupName;
    private int dataOfStart;
    private int dataOfFinish;
    private Long courseId;
}
