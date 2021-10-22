package by.chebyshev.project.repository;

import by.chebyshev.project.entity.Finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    Finance findFinanceByUserId(Long id);
}
