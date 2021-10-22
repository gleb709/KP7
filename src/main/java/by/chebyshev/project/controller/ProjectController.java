package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.sevice.ProjectService;
import by.chebyshev.project.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String projects(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                           Pagination<Project> pagination, Model model) {
        List<Project> list = projectService.findAll();
        model.addAttribute("projects", pagination.pageSelect(page, list));
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pagination.pageCount(list));
        return Page.PROJECT_LIST;
    }

    @GetMapping("/{id}")
    public String project(@PathVariable("id") Long id, Model model){
        Optional<Project> project = projectService.findProjectById(id);
        String rotation = Page.PROJECT_LIST;
        if(project.isPresent()){
            rotation = Page.PROJECT_INFO;

            model.addAttribute("project", project.get());
        }
        return rotation;
    }

    @GetMapping("/new")
    public String addProject(@ModelAttribute("project") Project project){
       return Page.NEW_PROJECT;
    }

    @PostMapping
    public String saveProject(@Valid Project project, BindingResult bindingResult){
        String rotation = Page.NEW_PROJECT;
        if(!bindingResult.hasErrors()){
            rotation = Redirect.PROJECT_LIST;
            projectService.saveProject(project);
        }
        return rotation;
    }
}
