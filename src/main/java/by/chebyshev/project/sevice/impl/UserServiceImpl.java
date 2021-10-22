package by.chebyshev.project.sevice.impl;

import by.chebyshev.project.entity.User;
import by.chebyshev.project.repository.UserRepository;
import by.chebyshev.project.sevice.UserService;
import by.chebyshev.project.util.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByAccount_Username(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserByPhone(String phone) {
        return userRepository.findUserByPhone(phone);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<String> updateUser(User user) {
        List<String> errors = new ArrayList<>();
        Optional<User> userCheck = findUserByUsername(user.getAccount().getUsername());
        if(userCheck.isPresent()){
            if(findUserByEmail(user.getEmail()).isPresent() && !findUserByEmail(user.getEmail()).get().getUsername().equals(user.getUsername())){
                errors.add("Email");
            }else if(findUserByPhone(user.getPhone()).isPresent() && !userCheck.get().getPhone().equals(user.getPhone())){
                errors.add("phone");
            } else{
                userCheck.get().setFirstname(user.getFirstname());
                userCheck.get().setLastname(user.getLastname());
                userCheck.get().setPhone(user.getPhone());
                userCheck.get().setEmail(user.getEmail());
                userRepository.save(userCheck.get());
            }
        }
        return errors;
    }

    @Override
    public List<String> addUser(User user) {
        List<String> errors = new ArrayList<>();
        Optional<User> usernameCheck = findUserByUsername(user.getAccount().getUsername());
        Optional<User> emailCheck = findUserByEmail(user.getEmail());
        if(usernameCheck.isPresent()){
            errors.add("username");
        }else if(emailCheck.isPresent()){
            errors.add("email");
        }else if(!user.getAccount().getPassword().equals(user.getAccount().getRepPassword())){
            errors.add("repPassword");
        }
        else {
            user.getAccount().setActive(true);
            user.getFinance().setPenalties(0);
            user.getFinance().setPrize(0);
            user.getAccount().setPassword(passwordEncoder.getPasswordEncoder().encode(user.getAccount().getPassword()));
            userRepository.save(user);
        }
        return errors;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByAccount_Username(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }
}
