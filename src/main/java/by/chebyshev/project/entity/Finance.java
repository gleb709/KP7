package by.chebyshev.project.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Min(value = 0, message = "Некорректная сумма")
    @Max(value = 10000000, message = "Некорректная сумма")
    private double salary;
    @NotNull
    @Min(value = 0, message = "Некорректная сумма")
    @Max(value = 100, message = "Некорректная сумма")    private int prize;
    @NotNull
    @Min(value = 0, message = "Некорректная сумма")
    @Max(value = 100, message = "Некорректная сумма")    private int penalties;

    @NotEmpty(message = "Введите должность")
    @Pattern(regexp = "[ 0-9a-zA-z]{2,30}", message = "Неверное значение")
    private String position;

    @OneToOne(mappedBy = "finance")
    private User user;

    @OneToMany(mappedBy = "finance", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<FinanceHistory> financeHistories;

    public Finance(){
    }

    public Finance(Long id, @NotNull @Size(min = 0, max = 100000000, message = "Некорректная сумма") int salary, @NotNull @Size(min = 0, max = 100, message = "Введите корректный процент премии") int prize, @NotNull @Size(min = 0, max = 100, message = "Введите корректный процент штрафа") int penalties, @NotEmpty(message = "Введите должность") @Pattern(regexp = "[ 0-9a-zA-z]{2,30}", message = "Неверное значение") String position, User user) {
        this.id = id;
        this.salary = salary;
        this.prize = prize;
        this.penalties = penalties;
        this.position = position;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getPrize() {
        return prize;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


