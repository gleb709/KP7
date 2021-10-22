package by.chebyshev.project.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Email
    @NotEmpty(message = "Введите Email")
    private String email;

    @Pattern(regexp = "[0-9]{11}", message = "Неверный номер")
    @NotEmpty(message = "Введите номер телефона")
    private String phone;

    @NotEmpty(message = "Введите имя")
    @Pattern(regexp = "[a-zA-z]{2,30}", message = "Некорректное имя(Используйте латинский алфавит)")
    private String firstname;

    @NotEmpty(message = "Введите фамилию")
    @Pattern(regexp = "[a-zA-z]{2,30}", message = "Некорректная фамилия(Используйте латинский алфавит)")
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectEmployee> projectEmployee;

    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "finance_id", referencedColumnName = "id")
    private Finance finance;

    public User() {
    }

    public User(long id, @Email @NotEmpty(message = "Введите Email") String email, @Length(min = 12, max = 12, message = "Неверный номер") @NotEmpty(message = "Введите номер телефона") String phone, @NotEmpty(message = "Введите имя") @Pattern(regexp = "[a-zA-z]{2,30}", message = "Некорректное имя(Используйте латинский алфавит)") String firstname, @NotEmpty(message = "Введите фамилию") @Pattern(regexp = "[a-zA-z]{2,30}", message = "Некорректная фамилия(Используйте латинский алфавит)") String lastname, @Valid Account account, List<ProjectEmployee> projectEmployee, @Valid Finance finance) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.firstname = firstname;
        this.lastname = lastname;
        this.account = account;
        this.projectEmployee = projectEmployee;
        this.finance = finance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<ProjectEmployee> getProjectEmployee() {
        return projectEmployee;
    }

    public void setProjectEmployee(List<ProjectEmployee> projectEmployee) {
        this.projectEmployee = projectEmployee;
    }
}