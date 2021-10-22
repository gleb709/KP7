package by.chebyshev.project.repository;

import by.chebyshev.project.entity.ProjectEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEmployeeRepository extends JpaRepository<ProjectEmployee, Long> {
    List<ProjectEmployee> findProjectEmployeeByProjectId(Long id);
}
