package peaksoft.springbootlesson.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import peaksoft.springbootlesson.dto.GroupRequest;
import peaksoft.springbootlesson.dto.GroupResponse;
import peaksoft.springbootlesson.dto.GroupResponseView;
import peaksoft.springbootlesson.entity.Course;
import peaksoft.springbootlesson.entity.Group;
import peaksoft.springbootlesson.repository.CourseRepository;
import peaksoft.springbootlesson.repository.GroupRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;

    public List<GroupResponse> getAllGroup() {
        List<GroupResponse> groupResponseList = new ArrayList<>();
        for (Group group : groupRepository.findAll()) {
            groupResponseList.add(mapToResponse(group));
        }
        return groupResponseList;
    }

    public GroupResponse getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        return mapToResponse(group);
    }

    public GroupResponse saveGroup(GroupRequest request) {
        Group group = mapToEntity(request);
        Course course = courseRepository.findById(request.getCourseId()).get();
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        course.setGroupList(groupList);
        group.setCourseList(courseList);
        groupRepository.save(group);
        return mapToResponse(group);
    }

    public GroupResponse updateGroup(Long id, GroupRequest request) {
        Group group = groupRepository.findById(id).get();
        group.setGroupName(request.getGroupName());
        group.setDataOfStart(request.getDataOfStart());
        group.setDataOfFinish(request.getDataOfFinish());
        Course course = courseRepository.findById(request.getCourseId()).get();
        List<Course> courseList = new ArrayList<>();
        courseList.add(course);
        List<Group> groupList = new ArrayList<>();
        groupList.add(group);
        course.setGroupList(groupList);
        group.setCourseList(courseList);
        groupRepository.save(group);
        return mapToResponse(group);
    }

    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        groupRepository.delete(group);

    }

    public Group mapToEntity(GroupRequest request) {
        Group group = new Group();
        group.setGroupName(request.getGroupName());
        group.setDataOfStart(request.getDataOfStart());
        group.setDataOfFinish(request.getDataOfFinish());
        return group;
    }

    public GroupResponse mapToResponse(Group group) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(group.getId());
        groupResponse.setGroupName(group.getGroupName());
        groupResponse.setDateOfStart(group.getDataOfStart());
        groupResponse.setDateOfFinish(group.getDataOfFinish());
        groupResponse.setLocalDate(LocalDate.now());
        groupResponse.setCourseList(group.getCourseList());
        return groupResponse;
    }

    public GroupResponseView searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        GroupResponseView responseView = new GroupResponseView();
        responseView.setGroupResponseViewList(view(search(text, pageable)));
        return responseView;
    }

    public List<GroupResponse> view(List<Group> groupList) {
        List<GroupResponse> groupResponseList = new ArrayList<>();
        for (Group group : groupList) {
            groupResponseList.add(mapToResponse(group));
        }
        return groupResponseList;
    }

    public List<Group> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return groupRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
