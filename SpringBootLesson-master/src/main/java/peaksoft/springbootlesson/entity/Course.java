package peaksoft.springbootlesson.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private String durationMonth;
    @CreatedDate
    private LocalDate localDate;
    private Boolean isActive = true;
    private Boolean isDeleted = false;
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    private Company company;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.ALL},mappedBy = "courseList")
    @JsonIgnore
    private List<Group> groupList;
    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "course")
    @JsonIgnore
    private User teacher;

    public void delete(){
        setTeacher(null);
    }
}