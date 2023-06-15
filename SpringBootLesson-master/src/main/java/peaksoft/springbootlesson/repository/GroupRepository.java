package peaksoft.springbootlesson.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.springbootlesson.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Long> {

    @Query("select c from Group c where upper(c.groupName) like concat('%',:text,'%')")
    List<Group> searchAndPagination(@Param("text")String text,Pageable pageable);
}
