package by.chebyshev.project.controller;

import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.FinanceHistory;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.sevice.impl.FinanceServiceImpl;
import by.chebyshev.project.util.Pagination;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/finance")
public class FinanceController {
    private final FinanceServiceImpl financeService;

    public FinanceController(FinanceServiceImpl financeService) {
        this.financeService = financeService;
    }

    @GetMapping
    public String finance(@RequestParam("page") int page, Pagination<User> pagination, Model model) {
        List<User> list = financeService.findAllUserFinance();
        model.addAttribute("users", pagination.pageSelect(page, list));
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pagination.pageCount(list));
        return Page.FINANCE_LIST;
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
            rotation = Page.USER_FINANCE;
        }
        return rotation;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> accountCheck = financeService.findUserFinanceByUsername(userDetails.getUsername());
        Optional<Finance> financeCheck = financeService.findUserFinance(id);

        String rotation = Page.USER_FINANCE;
        if(financeCheck.isPresent() && accountCheck.isPresent()){
            rotation = Page.UPDATE_FINANCE;
            model.addAttribute("finance", financeCheck.get());
        }
        return rotation;
    }

    @GetMapping("/history")
    public String history(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                          Pagination<FinanceHistory> pagination, Model model){
        List<FinanceHistory> list = financeService.findAllFinanceHistory();
        model.addAttribute("financeHistory", pagination.pageSelect(page, list));
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pagination.pageCount(list));
        return Page.FINANCE_HISTORY;
    }

    @GetMapping("/history/{id}")
    public String userFinanceHistory(@PathVariable("id") Long id, @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     Pagination<FinanceHistory> pagination, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userFinance = financeService.findUserFinanceByUsername(userDetails.getUsername());
        if(userFinance.isPresent()) {
            List<FinanceHistory> list = financeService.findUserFinanceHistoryById(id);
            model.addAttribute("financeHistory", pagination.pageSelect(page, list));
            model.addAttribute("page", page);
            model.addAttribute("pageCount", pagination.pageCount(list));
            model.addAttribute("user", userFinance.get());
        }
        return Page.USER_FINANCE_HISTORY;
    }

    @GetMapping("/salary")
    public String salary(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                         Pagination<User> pagination, Model model) {
        List<User> list = financeService.findAllUserFinance();
        model.addAttribute("users", pagination.pageSelect(page, list));
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pagination.pageCount(list));
        return Page.SALARY_LIST;
    }

    @GetMapping("/salary/{id}")
    private String userSalary(@PathVariable("id") Long id, FinanceHistory financeHistory, Model model){
        Optional<Finance> userFinance = financeService.findUserFinance(id);
        String rotation = Redirect.FINANCE_LIST;
        if(userFinance.isPresent()){
            financeHistory.setSalary(financeService.userSalary(userFinance.get()));
            model.addAttribute("finance", userFinance.get());
            model.addAttribute("financeHistory", financeHistory);
            rotation = Page.TRANSACTION;
        }
        return rotation;
    }

    @PostMapping("/transaction/{id}")
    public String transaction(@PathVariable("id") Long id, FinanceHistory financeHistory){
        financeService.transaction(financeHistory, id);
        return Redirect.SALARY_LIST + Page.FIRST_PAGE;
    }

    @PostMapping("/update")
    public String update(Finance finance){
            financeService.updateFinance(finance);
            return Redirect.FINANCE_LIST + "/" + finance.getId();
    }
}
