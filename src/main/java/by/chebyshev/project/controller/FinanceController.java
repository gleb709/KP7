package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.FinanceHistory;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.sevice.impl.FinanceServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    private static final int DEFAULT_SIZE = 10;
    private final FinanceServiceImpl financeService;

    public FinanceController(FinanceServiceImpl financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public String finance(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<User> list = financeService.findAllUserFinance(pageable);
        model.addAttribute("users", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.FINANCE_LIST;
    }

    @GetMapping("/{id}")
    public String userFinance(@PathVariable("id") Long id, Model model){
        Optional<Finance> userFinance = financeService.findUserFinance(id);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> finance = financeService.findUserFinanceByUsername(userDetails.getUsername());
        String rotation = Redirect.FINANCE_LIST;
        if(userFinance.isPresent() && finance.isPresent()){
            model.addAttribute("user", finance.get());
            model.addAttribute("finance", userFinance.get());
            rotation = RedirectPage.USER_FINANCE;
        }
        return rotation;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = financeService.findUserFinanceByUsername(userDetails.getUsername());
        Optional<Finance> financeCheck = financeService.findUserFinance(id);

        String rotation = RedirectPage.USER_FINANCE;
        if(financeCheck.isPresent() && accountCheck.isPresent()){
            rotation = RedirectPage.UPDATE_FINANCE;
            model.addAttribute("finance", financeCheck.get());
        }
        return rotation;
    }

    @GetMapping("/history")
    public String history(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<FinanceHistory> list = financeService.findAllFinanceHistory(pageable);
        model.addAttribute("financeHistory", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.FINANCE_HISTORY;
    }

    @GetMapping("/history/{id}")
    public String userFinanceHistory(@PathVariable("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userFinance = financeService.findUserFinanceByUsername(userDetails.getUsername());
        if(userFinance.isPresent()) {
            Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
            Page<FinanceHistory> list = financeService.findUserFinanceHistoryById(id, pageable);
            model.addAttribute("financeHistory", list.getContent());
            model.addAttribute("page", list.getNumber());
            model.addAttribute("pageCount", list.getTotalPages()-1);
            model.addAttribute("user", userFinance.get());
        }
        return RedirectPage.USER_FINANCE_HISTORY;
    }

    @GetMapping("/salary")
    public String salary(@RequestParam(value = "page", required = false, defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, DEFAULT_SIZE);
        Page<User> list = financeService.findAllUserFinance(pageable);
        model.addAttribute("users", list.getContent());
        model.addAttribute("page", list.getNumber());
        model.addAttribute("pageCount", list.getTotalPages()-1);
        return RedirectPage.SALARY_LIST;
    }

    @GetMapping("/salary/{id}")
    private String userSalary(@PathVariable("id") Long id, FinanceHistory financeHistory, Model model){
        Optional<Finance> userFinance = financeService.findUserFinance(id);
        String rotation = Redirect.FINANCE_LIST;
        if(userFinance.isPresent()){
            financeHistory.setSalary(financeService.userSalary(userFinance.get()));
            model.addAttribute("finance", userFinance.get());
            model.addAttribute("financeHistory", financeHistory);
            rotation = RedirectPage.TRANSACTION;
        }
        return rotation;
    }

    @PostMapping("/transaction/{id}")
    public String transaction(@PathVariable("id") Long id, FinanceHistory financeHistory){
        financeService.transaction(financeHistory, id);
        return Redirect.SALARY_LIST + RedirectPage.FIRST_PAGE;
    }

    @PostMapping("/update")
    public String update(Finance finance){
            financeService.updateFinance(finance);
            return Redirect.FINANCE_LIST + "/" + finance.getId();
    }
}
