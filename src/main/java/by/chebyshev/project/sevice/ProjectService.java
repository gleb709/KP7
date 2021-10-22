package by.chebyshev.project.sevice;

import by.chebyshev.project.entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll();
    Optional<Project> findProjectById(Long id);
    void saveProject(Project project);

}
