package peaksoft.springbootlesson.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.springbootlesson.dto.GroupRequest;
import peaksoft.springbootlesson.dto.GroupResponse;
import peaksoft.springbootlesson.dto.GroupResponseView;
import peaksoft.springbootlesson.service.GroupService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ADMIN','INSTRUCTOR')")
@Tag(name = "Group Auth", description = "We can create new Group")
@RequestMapping("/api/groups")
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/all")
    public List<GroupResponse> getAllGroup() {
        return groupService.getAllGroup();
    }

    @GetMapping("{id}")
    public GroupResponse getGroup(@PathVariable("id") Long id) {
        return groupService.getGroupById(id);
    }

    @PostMapping("addGroup")
    public GroupResponse save(@RequestBody GroupRequest groupRequest) {
        return groupService.saveGroup(groupRequest);
    }

    @PutMapping("{id}")
    public GroupResponse update(@PathVariable("id") Long id, @RequestBody GroupRequest groupRequest) {
        return groupService.updateGroup(id, groupRequest);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
        return "Successfully deleted Group with id: " + id;
    }

    @GetMapping
    public GroupResponseView getAllGroups(@RequestParam(name = "text", required = false) String text,
                                          @RequestParam int page,
                                          @RequestParam int size) {
        return groupService.searchAndPagination(text, page, size);
    }

}
