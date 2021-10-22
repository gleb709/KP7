package by.chebyshev.project.sevice;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.entity.ProjectEmployee;
import by.chebyshev.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface ProjectEmployeeService {
    List<ProjectEmployee> findProjectEmployeeByProjectId(Long id);
    List<User> findNotProjectUsers(Long id);
    Optional<Project> findProjectById(Long id);
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    Optional<ProjectEmployee> findEmployeeById(Long id);
    void addEmployee(ProjectEmployee projectEmployee);
    void deleteEmployee(Long id);

}
