package by.chebyshev.project.sevice.impl;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.entity.ProjectEmployee;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.repository.ProjectRepository;
import by.chebyshev.project.sevice.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserServiceImpl userService;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserServiceImpl userService) {
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        return projectRepository.findProjectById(id);
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }
}
