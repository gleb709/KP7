package by.chebyshev.project.repository;

import by.chebyshev.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findUserByAccount_Username(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Page<User> findAll(Pageable pageable);
}
