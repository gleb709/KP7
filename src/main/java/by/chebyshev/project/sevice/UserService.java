package by.chebyshev.project.sevice;

import by.chebyshev.project.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhone(String phone);
    List<User> findAllUsers();
    List<String> updateUser(User user);
    List<String> addUser(User user);

}
