package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Account;
import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.sevice.UserService;
import by.chebyshev.project.util.Pagination;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final Pagination<User> pagination;

    public UserController(UserService userService, Pagination<User> pagination) {
        this.userService = userService;
        this.pagination = pagination;
    }

    @GetMapping
    public String userList(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        List<User> list = userService.findAllUsers();
        model.addAttribute("users", pagination.pageSelect(page, list));
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pagination.pageCount(list));
        return Page.USER_LIST;
    }

    @GetMapping("/{id}")
    public String userInfo(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = userService.findUserByUsername(userDetails.getUsername());
        Optional<User> userCheck = userService.findUserById(id);
        if(accountCheck.isPresent() && userCheck.isPresent()) {
            model.addAttribute("role", accountCheck.get().getAccount().getRole());
            model.addAttribute("user", userCheck.get());
        }
        return Page.USER_INFO;
    }

    @GetMapping("/new")
    public String newUser(User user, Account account, Finance finance, Model model){
        account.setRole(Role.USER.toString());
        user.setAccount(account);
        user.setFinance(finance);
        model.addAttribute("user", user);
        return Page.SIGN_UP;
    }

    @PostMapping("/signUp")
    public String addUser(User user, Model model) {
        String rotation = Redirect.USER_MENU;
        List<String> errors = userService.addUser(user);
        if (!errors.isEmpty()) {
            for (String error: errors) {
                model.addAttribute(error, true);
            }
            rotation = Page.SIGN_UP;
        }
        return rotation;
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = userService.findUserByUsername(userDetails.getUsername());
        Optional<User> userCheck = userService.findUserById(id);
        String rotation = Page.USER_MENU;
        if(userCheck.isPresent() && accountCheck.isPresent()){
            rotation = Page.UPDATE_USER;
            model.addAttribute("role", accountCheck.get().getAccount().getRole());
            model.addAttribute("user", userCheck.get());
        }
        return rotation;
    }

    @PostMapping("/update")
    public String updateUser(User user, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userCheck = userService.findUserByUsername(userDetails.getUsername());
        String rotation = Page.UPDATE_USER;
        if(userCheck.isPresent()){
            List<String> errors = userService.updateUser(user);
            model.addAttribute("user", userCheck.get());
            if (errors.isEmpty()) {
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
        Optional<User> user = userService.findUserById(id);
        user.ifPresent(value -> model.addAttribute("user", value));
        return Page.USER_PROJECTS;
    }

    @GetMapping("/menu")
    public String userMenu(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userService.findUserByUsername(userDetails.getUsername());
        user.ifPresent(value -> model.addAttribute("user", value));
        return Page.USER_MENU;
    }

}
