package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.entity.ProjectEmployee;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.sevice.impl.ProjectEmployeeServiceImpl;
import by.chebyshev.project.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class ProjectEmployeeController {

    private static final int DEFAULT_SIZE = 10;
    private final ProjectEmployeeServiceImpl projectEmployeeService;

    public ProjectEmployeeController(ProjectEmployeeServiceImpl projectEmployeeService) {
        this.projectEmployeeService = projectEmployeeService;
    }

    @GetMapping("/add/{id}")
    public String addEmployee(@PathVariable("id") Long id,
                              @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                              Pagination<User> pagination, Model model){
        Optional<Project> project = projectEmployeeService.findProjectById(id);
        List<User> userList = projectEmployeeService.findNotProjectUsers(id);
        String rotation = Redirect.PROJECT_LIST + "/" + id;
        if(project.isPresent()){
            model.addAttribute("project", project.get());
            model.addAttribute("users", pagination.pageSelect(page, userList));
            model.addAttribute("page", page);
            model.addAttribute("pageCount", pagination.pageCount(userList));
            rotation = RedirectPage.ADD_EMPLOYEE;
        }
        return rotation;
    }

    @PostMapping("/add")
    public String add(@RequestParam("projectId") Long projectId, @RequestParam("userId") Long userId,
                      @Valid ProjectEmployee projectEmployee, BindingResult bindingResult, Model model){
        Optional<Project> projectCheck = projectEmployeeService.findProjectById(projectId);
        Optional<User> userCheck = projectEmployeeService.findUserById(userId);
        String rotation = RedirectPage.ADD_EMPLOYEE;
        if(projectCheck.isPresent() && userCheck.isPresent() && !bindingResult.hasErrors()) {
            projectEmployee.setProject(projectCheck.get());
            projectEmployee.setUser(userCheck.get());
            projectEmployeeService.addEmployee(projectEmployee);
            model.addAttribute("id", projectCheck.get().getId());
            model.addAttribute("page",  "0");
            rotation = Redirect.PROJECT_LIST + "/" + projectCheck.get().getId();
        }
        return rotation;
    }

    @GetMapping("/{id}")
    public String projectEmployee(@PathVariable("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<ProjectEmployee> projectEmployees = projectEmployeeService.findProjectEmployeeByProjectId(id, pageable);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = projectEmployeeService.findUserByUsername(userDetails.getUsername());
        Optional<Project> project = projectEmployeeService.findProjectById(id);
        String rotation = RedirectPage.PROJECT_EMPLOYEE;
        if(project.isPresent() && user.isPresent()) {
            model.addAttribute("user", user.get());
            model.addAttribute("project", project.get());
            model.addAttribute("employees", projectEmployees.getContent());
            model.addAttribute("page", projectEmployees.getNumber());
            model.addAttribute("pageCount", projectEmployees.getTotalPages()-1);
        }
        return rotation;
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, @RequestParam("projectId") Long projectId, Model model) {
        Optional<Project> project = projectEmployeeService.findProjectById(projectId);
        if (project.isPresent()) {
            projectEmployeeService.deleteEmployee(id);
            model.addAttribute("id", projectId);
        }
        return Redirect.PROJECT_LIST + "/" + projectId;
    }
}
