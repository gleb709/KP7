package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Project;
import by.chebyshev.project.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private static final int DEFAULT_SIZE = 10;
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String projects(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<Project> list = projectService.findAll(pageable);
        model.addAttribute("projects", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.PROJECT_LIST;
    }

    @GetMapping("/{id}")
    public String project(@PathVariable("id") Long id, Model model){
        Optional<Project> project = projectService.findById(id);
        String rotation = RedirectPage.PROJECT_LIST;
        if(project.isPresent()){
            rotation = RedirectPage.PROJECT_INFO;

            model.addAttribute("project", project.get());
        }
        return rotation;
    }

    @GetMapping("/new")
    public String addProject(@ModelAttribute("project") Project project){
       return RedirectPage.NEW_PROJECT;
    }

    @PostMapping
    public String saveProject(@Valid Project project, BindingResult bindingResult){
        String rotation = RedirectPage.NEW_PROJECT;
        if(!bindingResult.hasErrors()){
            rotation = Redirect.PROJECT_LIST;
            projectService.saveProject(project);
        }
        return rotation;
    }
}
