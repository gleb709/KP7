package by.chebyshev.project.sevice;

import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.FinanceHistory;
import by.chebyshev.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface FinanceService {
    void updateFinance(Finance finance);
    void transaction(FinanceHistory financeHistory, Long id);
    double userSalary(Finance finance);
    Optional<Finance> findUserFinance(Long id);
    Optional<User> findUserFinanceByUsername(String username);
    List<FinanceHistory> findAllFinanceHistory();
    List<FinanceHistory> findUserFinanceHistoryById(Long id);
    List<User> findAllUserFinance();
}
