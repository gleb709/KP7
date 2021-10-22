package by.chebyshev.project.repository;

import by.chebyshev.project.entity.FinanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceHistoryRepository extends JpaRepository<FinanceHistory, Long> {
    List<FinanceHistory> findFinanceHistoryByFinanceId(Long id);
}
