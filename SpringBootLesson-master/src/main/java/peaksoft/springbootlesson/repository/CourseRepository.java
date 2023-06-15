package peaksoft.springbootlesson.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.springbootlesson.entity.Course;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where upper(c.courseName) like  concat('%',:text,'%') " +
            "or upper(c.company) like concat('%',:text,'%') ")

    List<Course> searchAndPagination(@Param("text")String text, Pageable pageable);

}