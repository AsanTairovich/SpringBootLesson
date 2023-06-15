package peaksoft.springbootlesson.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springbootlesson.dto.TeacherRequest;
import peaksoft.springbootlesson.dto.TeacherResponse;
import peaksoft.springbootlesson.dto.TeacherResponseView;
import peaksoft.springbootlesson.entity.*;
import peaksoft.springbootlesson.repository.CourseRepository;
import peaksoft.springbootlesson.repository.UserRepository;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<TeacherResponse> getAllTeacher() {
        List<TeacherResponse> teacherResponseList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRole() == Role.INSTRUCTOR) {
                teacherResponseList.add(mapToResponse(user));
            }
        }
        return teacherResponseList;
    }

    public TeacherResponse getTeacherById(Long id) {
        User user = userRepository.findById(id).get();
        return mapToResponse(user);
    }

    public TeacherResponse create(TeacherRequest request) {
        User user = new User();
        user.setFirstName(request.getFirsName());
        user.setEmail(request.getEmail());
        user.setLastName(request.getLsatName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.INSTRUCTOR);
        user.setLocalDate(LocalDate.now());
        Course course = courseRepository.findById(request.getCourseId()).get();
        user.setCourse(course);
        course.setTeacher(user);
        userRepository.save(user);
        return mapToResponse(user);
    }

    public TeacherResponse update(Long teacherId, TeacherRequest request) {
        User user = userRepository.findById(teacherId).get();
        user.setFirstName(request.getFirsName());
        user.setEmail(request.getEmail());
        user.setLastName(request.getLsatName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRoleName()));
        Course course = courseRepository.findById(request.getCourseId()).get();
        course.setTeacher(user);
        user.setCourse(course);
        user.setLocalDate(LocalDate.now());
        userRepository.save(user);
        return mapToResponse(user);
    }

    public String delete(Long id) {
        User user = userRepository.findById(id).get();
        if (user.getRole() == Role.INSTRUCTOR) {
            user.delete();
            userRepository.delete(user);
            return "Successfully deleted teacher with id : " + id;
        }else {
            return "the end";
        }
    }

    public TeacherResponse mapToResponse(User user) {
        TeacherResponse teacherResponse = new TeacherResponse();
        teacherResponse.setId(user.getId());
        teacherResponse.setFirsName(user.getFirstName());
        teacherResponse.setEmail(user.getEmail());
        teacherResponse.setLastName(user.getLastName());
        teacherResponse.setRoleName(user.getRole().name());
        teacherResponse.setCourse(user.getCourse());
        teacherResponse.setLocalDate(user.getLocalDate());
        return teacherResponse;
    }
    public TeacherResponseView searchAndPagination (String text, int page, int size){
        Pageable pageable = PageRequest.of(page - 1 , size);
        TeacherResponseView teacherResponseView = new TeacherResponseView();
        teacherResponseView.setTeacherResponseViewList(view(searchTeacher(text,pageable)));
        return teacherResponseView;
    }
    public List<TeacherResponse> view(List<User> teacherList){
        List<TeacherResponse> teacherResponseList = new ArrayList<>();
        for (User user : teacherList) {
            teacherResponseList.add(mapToResponse(user));
        }
        return teacherResponseList;
    }
    public List<User> searchTeacher(String text, Pageable pageable){
        String name = text == null ? "" : text;
        List<User> userList = userRepository.searchAndPagination(name.toUpperCase(),pageable);
        List<User> teacherList = new ArrayList<>();
        for (User user : userList) {
            if (user.getRole() == Role.INSTRUCTOR){
                teacherList.add(user);
            }
        }
        return teacherList;
    }

}
