package by.chebyshev.project.service;

import by.chebyshev.project.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService extends BaseService<Project>{
    Page<Project> findAll(Pageable pageable);
    void saveProject(Project project);

}
