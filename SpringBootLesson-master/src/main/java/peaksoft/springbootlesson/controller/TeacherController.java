package peaksoft.springbootlesson.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootlesson.dto.TeacherRequest;
import peaksoft.springbootlesson.dto.TeacherResponse;
import peaksoft.springbootlesson.dto.TeacherResponseView;
import peaksoft.springbootlesson.service.TeacherService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
@Tag(name = "Teacher Auth", description = "We can create new Teacher")
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping("all")
    public List<TeacherResponse> getAll() {
        return teacherService.getAllTeacher();
    }

    @GetMapping("{id}")
    public TeacherResponse getById(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping("addTeacher")
    public TeacherResponse create(@RequestBody TeacherRequest request) {
        return teacherService.create(request);
    }

    @PutMapping("{id}")
    public TeacherResponse update(@PathVariable("id") Long id, @RequestBody TeacherRequest request) {
        return teacherService.update(id, request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        return teacherService.delete(id);
    }
    @GetMapping()
    public TeacherResponseView getAllTeacher(@RequestParam(name = "text",required = false) String text,
                                             @RequestParam int page,
                                             @RequestParam int size){
        return teacherService.searchAndPagination(text,page,size);
    }
}
