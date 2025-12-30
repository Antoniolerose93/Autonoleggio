package auto.auto.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "rental")

public class Rental {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private String rentDescription;

@NotNull
    private LocalDate rentStartDate;

@NotNull
    private LocalDate rentEndDate;

@ManyToOne
@JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;

@ManyToOne(cascade = CascadeType.PERSIST)
@JoinColumn(name = "drivers_id", nullable = false)
    private Drivers driver;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRentDescription() {
        return rentDescription;
    }

    public void setRentDescription(String rentDescription) {
        this.rentDescription = rentDescription;
    }

    public LocalDate getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(LocalDate rentStartDate) {
        this.rentStartDate = rentStartDate; 
    }

    public LocalDate getRentEndDate() {
        return rentEndDate; 
    }

    public void setRentEndDate(LocalDate rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Drivers getDriver() {
        return driver;
    }

    public void setDriver(Drivers driver) {
        this.driver = driver;
    }



}
