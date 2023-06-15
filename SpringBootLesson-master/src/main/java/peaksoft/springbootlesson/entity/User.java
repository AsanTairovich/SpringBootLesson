package peaksoft.springbootlesson.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @CreatedDate
    private LocalDate localDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH, PERSIST,CascadeType.DETACH})
    @JsonIgnore
    @JoinColumn(name = "group_id")
    private Group group;
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE, DETACH})
    @JsonIgnore
    @JoinColumn(name = "course_id")
    private Course course;
    @Column(name = "study_format")
    @Enumerated(EnumType.STRING)
    private StudyFormation studyFormation;

    public void delete(){
        course.setTeacher(null);
        setCourse(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
