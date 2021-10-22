package by.chebyshev.project.entity;


import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Pattern(regexp = "[0-9a-zA-z]{5,30}", message = "Login should be 5-30 letters")
    private String username;
    private String password;
    @Transient
    private String repPassword;
    private String role;
    private boolean isActive;

    @OneToOne(mappedBy = "account")
    private User user;

    public Account() {
    }

    public Account(Long id, @Length(min = 5, max = 30, message = "Login should be 5-30 letters") String username, @Pattern(regexp = "[0-9a-zA-z]{6,30}", message = "Password should be 6-30 english letters") String password,
                   String repPassword, String role, boolean isActive, User user) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.repPassword = repPassword;
        this.role = role;
        this.isActive = isActive;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
