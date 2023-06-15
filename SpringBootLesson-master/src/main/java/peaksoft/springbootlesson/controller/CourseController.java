package peaksoft.springbootlesson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootlesson.dto.CourseRequest;
import peaksoft.springbootlesson.dto.CourseResponse;
import peaksoft.springbootlesson.dto.CourseResponseView;
import peaksoft.springbootlesson.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
public class CourseController {
    private final CourseService courseService;
    @GetMapping("all")
    public List<CourseResponse> getAll(){
        return courseService.getAllCourses();
    }
    @GetMapping("{id}")
    public CourseResponse getById(@PathVariable("id")Long id){
        return courseService.getCourseById(id);
    }
    @PostMapping("addCourse")
    public CourseResponse create(@RequestBody CourseRequest request){
        return courseService.create(request);
    }
    @PatchMapping("{id}")
    public CourseResponse update(@PathVariable("id")Long id,@RequestBody CourseRequest request){
        return courseService.updateCourse(id,request);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id){
         courseService.delete(id);
         return "Successfully Course deleted with id: "+id;
    }
    @GetMapping()
    public CourseResponseView getAllCourse(@RequestParam(name = "text",required = false)String text,
                                           @RequestParam int page,
                                           @RequestParam int size){
        return courseService.searchAndPagination(text,page,size);
    }
}
