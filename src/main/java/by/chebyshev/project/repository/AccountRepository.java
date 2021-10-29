package by.chebyshev.project.repository;

import by.chebyshev.project.entity.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountById(Long id);
}
