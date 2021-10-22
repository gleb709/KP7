package by.chebyshev.project.repository;

import by.chebyshev.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByAccount_Username(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
}
