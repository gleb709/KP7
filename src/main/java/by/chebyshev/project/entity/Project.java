package by.chebyshev.project.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    @Min(value = 0, message = "Некорректная сумма")
    @Max(value = 1000000000, message = "Некорректная сумма")
    private double price;
    @NotNull
    @DateTimeFormat
    private Date expirationDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProjectEmployee> projectEmployee;

    public Project() {
    }

    public Project(Long id, @NotNull String name, @NotNull @Min(value = 0, message = "Некорректная сумма") @Max(value = 1000000000, message = "Некорректная сумма") double price, @NotNull Date expirationDate, List<ProjectEmployee> projectEmployee) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
        this.projectEmployee = projectEmployee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<ProjectEmployee> getProjectEmployee() {
        return projectEmployee;
    }

    public void setProjectEmployee(List<ProjectEmployee> projectEmployee) {
        this.projectEmployee = projectEmployee;
    }
}
