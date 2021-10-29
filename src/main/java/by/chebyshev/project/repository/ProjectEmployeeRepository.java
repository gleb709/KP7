package by.chebyshev.project.repository;

import by.chebyshev.project.entity.ProjectEmployee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEmployeeRepository extends BaseRepository<ProjectEmployee> {
    Page<ProjectEmployee> findProjectEmployeeByProjectId(Long id, Pageable pageable);
    List<ProjectEmployee> findProjectEmployeeByProjectId(Long id);
}
