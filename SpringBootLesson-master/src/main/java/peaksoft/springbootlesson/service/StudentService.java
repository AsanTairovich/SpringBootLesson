package peaksoft.springbootlesson.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springbootlesson.dto.StudentRequest;
import peaksoft.springbootlesson.dto.StudentResponse;
import peaksoft.springbootlesson.dto.StudentResponseView;
import peaksoft.springbootlesson.entity.Group;
import peaksoft.springbootlesson.entity.Role;
import peaksoft.springbootlesson.entity.StudyFormation;
import peaksoft.springbootlesson.entity.User;
import peaksoft.springbootlesson.repository.GroupRepository;
import peaksoft.springbootlesson.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;

    private final GroupRepository groupRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<StudentResponse> getAllStudent() {
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.STUDENT) {
                studentResponseList.add(mapToResponse(user));
            }
        }
        return studentResponseList;
    }

    public StudentResponse getStudentById(Long studentId) {
        User user = userRepository.findById(studentId).get();
        return mapToResponse(user);
    }

    public StudentResponse create(StudentRequest studentRequest) {
        User user = new User();
        user.setFirstName(studentRequest.getFirsName());
        user.setEmail(studentRequest.getEmail());
        user.setLastName(studentRequest.getLastName());
        user.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        user.setStudyFormation(StudyFormation.valueOf(studentRequest.getStudyFormation()));
        user.setRole(Role.STUDENT);
        user.setLocalDate(LocalDate.now());
        Group group = groupRepository.findById(studentRequest.getGroupId()).get();
        List<User> userList = new ArrayList<>();
        group.setStudentList(userList);
        user.setGroup(group);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public StudentResponse mapToResponse(User user) {
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setId(user.getId());
        studentResponse.setFirsName(user.getFirstName());
        studentResponse.setEmail(user.getEmail());
        studentResponse.setLastName(user.getLastName());
        studentResponse.setRoleName(user.getRole().name());
        studentResponse.setStudyFormation(user.getStudyFormation().toString());
        studentResponse.setLocalDate(user.getLocalDate());
        studentResponse.setGroup(user.getGroup());
        return studentResponse;
    }

    public StudentResponse updateStudent(Long studentId, StudentRequest request) {
        User user = userRepository.findById(studentId).get();
        user.setFirstName(request.getFirsName());
        user.setEmail(request.getEmail());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRoleName()));
        user.setStudyFormation(StudyFormation.valueOf(request.getStudyFormation()));
        Group group = groupRepository.findById(request.getGroupId()).get();
        user.setGroup(group);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public String delete(Long studentId) {
        userRepository.deleteById(studentId);
        return "Successfully deleted student with id : " + studentId;
    }

    public StudentResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        StudentResponseView studentResponseView = new StudentResponseView();
        studentResponseView.setStudentResponseViewList(view(searchStudent(text, pageable)));
        return studentResponseView;
    }

    public List<StudentResponse> view(List<User> studentList) {
        List<StudentResponse> studentResponseList = new ArrayList<>();
        for (User user : studentList) {
            studentResponseList.add(mapToResponse(user));
        }
        return studentResponseList;
    }

    public List<User> searchStudent(String text, org.springframework.data.domain.Pageable pageable) {
        String name = text == null ? "" : text;
        List<User> userList = userRepository.searchAndPagination(name.toUpperCase(), pageable);
        List<User> studentList = new ArrayList<>();
        for (User user : userList) {
            if (user.getRole() == Role.STUDENT){
                studentList.add(user);
            }
        }
        return studentList;
    }

}
