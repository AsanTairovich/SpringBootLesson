package peaksoft.springbootlesson.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springbootlesson.dto.*;
import peaksoft.springbootlesson.entity.Company;
import peaksoft.springbootlesson.entity.Course;
import peaksoft.springbootlesson.entity.Group;
import peaksoft.springbootlesson.repository.CompanyRepository;
import peaksoft.springbootlesson.repository.CourseRepository;
import peaksoft.springbootlesson.repository.GroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final CompanyRepository companyRepository;
    private final GroupRepository groupRepository;

    public List<CourseResponse> getAllCourses() {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for (Course course : courseRepository.findAll()) {
            courseResponseList.add(mapToResponse(course));
        }
        return courseResponseList;
    }

    public CourseResponse getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).get();
        return mapToResponse(course);
    }

    public CourseResponse create(CourseRequest request) {
        Course course = new Course();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        course.setLocalDate(LocalDate.now());
        course.setIsActive(course.getIsActive());
        course.setIsDeleted(course.getIsDeleted());
        Company company = companyRepository.findById(request.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);
        log.info("Saved course: " + course.getCourseName());
        return mapToResponse(course);
    }

    public CourseResponse updateCourse(Long courseId, CourseRequest request) {
        Course course = courseRepository.findById(courseId).get();
        course.setCourseName(request.getCourseName());
        course.setDurationMonth(request.getDurationMonth());
        course.setLocalDate(LocalDate.now());
        Company company = companyRepository.findById(request.getCompanyId()).get();
        course.setCompany(company);
        courseRepository.save(course);
        return mapToResponse(course);

    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseResponse mapToResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setCourseName(course.getCourseName());
        courseResponse.setDurationMonth(course.getDurationMonth());
        courseResponse.setCompanyId(course.getCompany().getId());
        courseResponse.setCompanyName(course.getCompany().getCompanyName());
        courseResponse.setLocalDate(course.getLocalDate());
        courseResponse.setIsActive(course.getIsActive());
        courseResponse.setIsDeleted(course.getIsDeleted());
        return courseResponse;
    }

    public CourseResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CourseResponseView courseResponseView = new CourseResponseView();
        courseResponseView.setCourseResponseViewList(view(search(text, pageable)));
        return courseResponseView;
    }

    public List<CourseResponse> view(List<Course> courseList) {
        List<CourseResponse> courseResponseList = new ArrayList<>();
        for (Course course : courseList) {
            courseResponseList.add(mapToResponse(course));
        }
        return courseResponseList;
    }

    private List<Course> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return courseRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
