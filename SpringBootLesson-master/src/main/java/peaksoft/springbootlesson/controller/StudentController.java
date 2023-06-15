package peaksoft.springbootlesson.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootlesson.dto.StudentRequest;
import peaksoft.springbootlesson.dto.StudentResponse;
import peaksoft.springbootlesson.dto.StudentResponseView;
import peaksoft.springbootlesson.repository.UserRepository;
import peaksoft.springbootlesson.service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/students")
@Tag(name = "Student Auth", description = "We can create new Student")
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR','STUDENT')")
public class StudentController {
    private final StudentService studentService;
    @GetMapping("all")
    public List<StudentResponse> getAll() {
        return studentService.getAllStudent();
    }

    @GetMapping("{id}")
    public StudentResponse getById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("addStudent")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public StudentResponse create(@RequestBody StudentRequest request) {
        return studentService.create(request);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public StudentResponse update(@PathVariable("id") Long id, @RequestBody StudentRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
    public String delete(@PathVariable("id") Long id) {
        return studentService.delete(id);
    }

    @GetMapping()
    public StudentResponseView getAllStudent(@RequestParam(name = "text",required = false) String text,
                                             @RequestParam int page,
                                             @RequestParam int size){
        return studentService.searchAndPagination(text,page,size);

    }
}
