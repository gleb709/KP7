package by.chebyshev.project.repository;

import by.chebyshev.project.entity.FinanceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceHistoryRepository extends BaseRepository<FinanceHistory> {
    Page<FinanceHistory> findFinanceHistoryByFinanceId(Long id, Pageable pageable);
}
