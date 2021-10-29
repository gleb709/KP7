package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Account;
import by.chebyshev.project.service.impl.AccountServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public String account(@PathVariable("id") Long id,Model model){
        Optional<Account> accountCheck = accountService.findById(id);
        String rotation = Redirect.USER_LIST;
        if(accountCheck.isPresent()){
            rotation = RedirectPage.ACCOUNT_INFO;
            model.addAttribute("account", accountCheck.get());
        }
        return rotation;
    }

    @GetMapping("update/{id}")
    public String accountUpdate(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> accountCheck = accountService.findAccountByUsername(userDetails.getUsername());
        Optional<Account> userCheck = accountService.findById(id);

        String rotation = RedirectPage.USER_LIST;
        if(userCheck.isPresent() && accountCheck.isPresent() && accountCheck.get().getRole().equals("ADMIN")){

            rotation = RedirectPage.UPDATE_ACCOUNT;
            model.addAttribute("account", userCheck.get());
        }
        return rotation;
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> accountCheck = accountService.findAccountByUsername(userDetails.getUsername());
        String rotation = Redirect.USER_MENU;
        if(accountCheck.isPresent()) {
            rotation = RedirectPage.CHANGE_PASSWORD;
            model.addAttribute("account", accountCheck.get());
        }
        return rotation;
    }

    @PostMapping("/changePassword")
    public String changePassword(@Valid Account account, BindingResult bindingResult, Model model){
        Optional<Account> accountCheck = accountService.findAccountByUsername(account.getUsername());
        String rotation = RedirectPage.CHANGE_PASSWORD;
        if(accountCheck.isPresent()) {
            List<String> errors = accountService.changePassword(account);
            if (errors.isEmpty() && !bindingResult.hasErrors()) {
                rotation = Redirect.USER_MENU;
            } else {
                model.addAttribute(accountCheck.get());
                for (String error : errors) {
                    model.addAttribute(error, true);
                }
            }
        }
        return rotation;
    }

    @PostMapping("/update")
    public String update(@Valid Account account, BindingResult bindingResult){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Account> userCheck = accountService.findAccountByUsername(userDetails.getUsername());
        Optional<Account> accountCheck = accountService.findAccountByUsername(account.getUsername());
        String rotation = Redirect.ACCOUNT_UPDATE + "/" + account.getId();

        if(userCheck.isPresent() && accountCheck.isPresent() && !bindingResult.hasErrors()){
            rotation = Redirect.ACCOUNT_INFO + "/" + accountCheck.get().getId();
            accountService.updateAccount(account);
        }
        return rotation;
    }


}
