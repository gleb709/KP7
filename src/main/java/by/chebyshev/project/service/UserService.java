package by.chebyshev.project.service;

import by.chebyshev.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User>{
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    Page<User> findAllUsers(Pageable pageable);
    List<User> findAllUsers();
    List<String> updateUser(User user);
    List<String> addUser(User user);

}
