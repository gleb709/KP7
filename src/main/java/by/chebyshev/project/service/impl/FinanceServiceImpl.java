package by.chebyshev.project.service.impl;

import by.chebyshev.project.entity.Finance;
import by.chebyshev.project.entity.FinanceHistory;
import by.chebyshev.project.entity.User;
import by.chebyshev.project.repository.FinanceHistoryRepository;
import by.chebyshev.project.repository.FinanceRepository;
import by.chebyshev.project.repository.UserRepository;
import by.chebyshev.project.service.FinanceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository financeRepository;
    private final FinanceHistoryRepository financeHistoryRepository;
    private final UserRepository userRepository;

    public FinanceServiceImpl(FinanceRepository financeRepository, FinanceHistoryRepository financeHistoryRepository, UserRepository userRepository) {
        this.financeRepository = financeRepository;
        this.financeHistoryRepository = financeHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void updateFinance(Finance finance) {
        Optional<Finance> financeCheck = financeRepository.findById(finance.getId());
        if(financeCheck.isPresent()){
           financeCheck.get().setSalary(finance.getSalary());
            financeCheck.get().setPrize(finance.getPrize());
            financeCheck.get().setPenalties(finance.getPenalties());
            financeCheck.get().setPosition(finance.getPosition());
            financeRepository.save(financeCheck.get());
        }
    }

    @Override
    public void transaction(FinanceHistory financeHistory, Long id) {
        Optional<Finance> financeCheck = financeRepository.findById(id);
        if(financeCheck.isPresent()){
            financeHistory.setFinance(financeCheck.get());
            financeHistory.setDate(new Date());
            financeHistoryRepository.save(financeHistory);
        }
    }

    @Override
    public double userSalary(Finance finance) {
        int maxPercent = 100;
        double salary = Math.round((finance.getSalary() -
                finance.getSalary() * finance.getPenalties()/maxPercent +
                finance.getSalary() * finance.getPrize()/maxPercent)*maxPercent);
        salary = salary/maxPercent + salary%maxPercent;
        return salary;
    }

    @Override
    public Optional<Finance> findUserFinance(Long id) {
        return financeRepository.findById(id);
    }

    @Override
    public Optional<User> findUserFinanceByUsername(String username) {
        return userRepository.findUserByAccount_Username(username);
    }

    @Override
    public Page<FinanceHistory> findAllFinanceHistory(Pageable pageable) {
        return financeHistoryRepository.findAll(pageable);
    }

    @Override
    public Page<FinanceHistory> findUserFinanceHistoryById(Long id, Pageable pageable) {
        Finance finance = financeRepository.findFinanceByUserId(id);
        return financeHistoryRepository.findFinanceHistoryByFinanceId(finance.getId(), pageable);
    }

    public Page<User> findAllUserFinance(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<Finance> findById(Long id) {
        return financeRepository.findById(id);
    }
}
