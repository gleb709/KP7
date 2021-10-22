package by.chebyshev.project.sevice.impl;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.entity.ProjectEmployee;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.repository.ProjectEmployeeRepository;
import by.chebyshev.project.sevice.ProjectEmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service public class ProjectEmployeeServiceImpl implements ProjectEmployeeService {

    private final ProjectEmployeeRepository projectEmployeeRepository;
    private final ProjectServiceImpl projectService;
    private final UserServiceImpl userService;

    public ProjectEmployeeServiceImpl(ProjectEmployeeRepository projectEmployeeRepository, ProjectServiceImpl projectService, UserServiceImpl userService) {
        this.projectEmployeeRepository = projectEmployeeRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public List<User> findNotProjectUsers(Long id) {
        List<User> userList = userService.findAllUsers();
        List<User> notProjectUsers = userService.findAllUsers();
        Optional<Project> project = projectService.findProjectById(id);
        if(project.isPresent()) {
            for (User user : userList) {
                for (ProjectEmployee employee: project.get().getProjectEmployee()) {
                    if(employee.getUser().getId() == user.getId()){
                        notProjectUsers.remove(user);
                    }
                }
            }
        }
        return notProjectUsers;
    }

    @Override
    public List<ProjectEmployee> findProjectEmployeeByProjectId(Long id) {
        return projectEmployeeRepository.findProjectEmployeeByProjectId(id);
    }

    @Override
    public Optional<Project> findProjectById(Long id) {
        return projectService.findProjectById(id);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userService.findUserById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userService.findUserByUsername(username);
    }

    @Override
    public Optional<ProjectEmployee> findEmployeeById(Long id) {
        return projectEmployeeRepository.findById(id);
    }

    @Override
    public void addEmployee(ProjectEmployee projectEmployee) {
        List<ProjectEmployee> employees = findProjectEmployeeByProjectId(projectEmployee.getProject().getId());
        if(employees.isEmpty()){
            projectEmployee.setEmployeeRole("Leader");
        }else{
            projectEmployee.setEmployeeRole("Developer");
        }
        projectEmployeeRepository.save(projectEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<ProjectEmployee> employee = findEmployeeById(id);
        if(employee.isPresent()){
            List<ProjectEmployee> employees = findProjectEmployeeByProjectId(employee.get().getProject().getId());
            if(employees.size() > 1 && employees.get(0).equals(employee.get())) {
                employees.get(1).setEmployeeRole("Leader");
                projectEmployeeRepository.save(employees.get(1));
            }
            employee.ifPresent(projectEmployeeRepository::delete);
        }
    }
}
