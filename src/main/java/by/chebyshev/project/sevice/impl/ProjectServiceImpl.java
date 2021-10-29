package by.chebyshev.project.sevice.impl;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.repository.ProjectRepository;
import by.chebyshev.project.sevice.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

    @Override
    public Optional<Project> findById(Long id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }
}
