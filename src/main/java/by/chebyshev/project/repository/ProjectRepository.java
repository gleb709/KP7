package by.chebyshev.project.repository;

import by.chebyshev.project.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends BaseRepository<Project> {
    Optional<Project> findProjectById(Long id);
}
