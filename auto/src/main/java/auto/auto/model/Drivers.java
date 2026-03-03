package auto.auto.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name ="drivers")

public class Drivers {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

@NotBlank(message="Inserisci il nome")
    private String name;

@NotBlank(message="Inserisci il cognome")
    private String surname;

@NotBlank(message ="patente obbligatoria")
@Size(min = 10, max = 10, message ="Il numero della patente è composto da 10 caratteri")
@Column(unique = true)
private String drivingLicense;

@OneToMany
    private List<Rental> rent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public List<Rental> getRent() {
        return rent;
    }

    public void setRent(List<Rental> rent) {
        this.rent = rent;
    }   


}
