package by.chebyshev.project.repository;

import by.chebyshev.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, List> {
    Optional<Project> findProjectById(Long id);
}
