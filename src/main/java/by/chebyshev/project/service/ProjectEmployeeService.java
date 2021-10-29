package by.chebyshev.project.service;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.entity.ProjectEmployee;
import by.chebyshev.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProjectEmployeeService extends BaseService<ProjectEmployee>{
    Page<ProjectEmployee> findProjectEmployeeByProjectId(Long id, Pageable pageable);
    List<ProjectEmployee> findProjectEmployeeByProjectId(Long id);
    List<User> findNotProjectUsers(Long id);
    Optional<Project> findProjectById(Long id);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    void addEmployee(ProjectEmployee projectEmployee);
    void deleteEmployee(Long id);

}
