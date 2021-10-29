package by.chebyshev.project.repository;

import by.chebyshev.project.entity.Finance;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceRepository extends BaseRepository<Finance> {
    Finance findFinanceByUserId(Long id);
}
