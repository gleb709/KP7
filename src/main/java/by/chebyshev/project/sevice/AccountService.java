package by.chebyshev.project.sevice;

import by.chebyshev.project.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountById(Long id);
    List<String> changePassword(Account account);
    void updateAccount(Account account);
}
