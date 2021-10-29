package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Account;
import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private static final int DEFAULT_SIZE = 10;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<User> list = userService.findAllUsers(pageable);
        model.addAttribute("users", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.USER_LIST;
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = userService.findUserByUsername(userDetails.getUsername());
        Optional<User> userCheck = userService.findById(id);
        if(accountCheck.isPresent() && userCheck.isPresent()) {
            model.addAttribute("role", accountCheck.get().getAccount().getRole());
            model.addAttribute("user", userCheck.get());
        }
        return RedirectPage.USER_INFO;
    }

    @GetMapping("/new")
    public String newUser(User user, Account account, Finance finance, Model model){
        account.setRole(Role.USER.toString());
        user.setAccount(account);
        user.setFinance(finance);
        model.addAttribute("user", user);
        return RedirectPage.SIGN_UP;
    }

    @PostMapping("/signUp")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        String rotation = Redirect.USER_MENU;
        List<String> errors = userService.addUser(user);
        if (!errors.isEmpty() && !bindingResult.hasErrors()) {
            for (String error: errors) {
                model.addAttribute(error, true);
            }
            rotation = RedirectPage.SIGN_UP;
        }
        return rotation;
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = userService.findUserByUsername(userDetails.getUsername());
        Optional<User> userCheck = userService.findById(id);
        String rotation = RedirectPage.USER_MENU;
        if(userCheck.isPresent() && accountCheck.isPresent()){
            rotation = RedirectPage.UPDATE_USER;
            model.addAttribute("role", accountCheck.get().getAccount().getRole());
            model.addAttribute("user", userCheck.get());
        }
        return rotation;
    }

    @PostMapping("/update")
    public String updateUser(@Valid User user, BindingResult bindingResult, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userCheck = userService.findUserByUsername(userDetails.getUsername());
        String rotation = RedirectPage.UPDATE_USER;
        if(userCheck.isPresent()){
            List<String> errors = userService.updateUser(user);
            model.addAttribute("user", userCheck.get());
            if (errors.isEmpty() && !bindingResult.hasErrors()) {
                rotation = Redirect.USER_MENU;
                if(!userCheck.get().getAccount().getUsername().equals(user.getAccount().getUsername())){
                    rotation = Redirect.USER_LIST;
                }
            }else{
                for (String error: errors) {
                    model.addAttribute(error, true);
                }
            }
        }
        return rotation;
    }

    @GetMapping("/project/{id}")
    public String userProject(@PathVariable("id") Long id, Model model){
        Optional<User> user = userService.findById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return RedirectPage.USER_PROJECTS;
    }

    @GetMapping("/menu")
    public String userMenu(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserByUsername(userDetails.getUsername());
        user.ifPresent(value -> model.addAttribute("user", value));
        return RedirectPage.USER_MENU;
    }

}
