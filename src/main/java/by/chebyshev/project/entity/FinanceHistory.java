package by.chebyshev.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class FinanceHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @DateTimeFormat
    private Date date;

    @NotNull
    @Min(value = 0, message = "Некорректная сумма")
    @Max(value = 10000000, message = "Некорректная сумма")
    private double salary;

    @ManyToOne
    @JoinColumn(name = "finance_id", referencedColumnName = "id")
    private Finance finance;

    public FinanceHistory() {
    }

    public FinanceHistory(Long id, @NotNull Date date, @NotNull @Min(value = 0, message = "Некорректная сумма") @Max(value = 10000000, message = "Некорректная сумма") double salary, Finance finance) {
        this.id = id;
        this.date = date;
        this.salary = salary;
        this.finance = finance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }
}

