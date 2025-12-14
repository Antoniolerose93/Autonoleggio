package auto.auto.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="offers")

public class Offer {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     private String offerDescription;

    private LocalDate offerStarDate;

    private LocalDate offerEnDate;

@ManyToOne
@JoinColumn(name = "auto_id", nullable = false)
    private Auto auto;
    
    public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getOfferDescription() {
    return offerDescription;
}

public void setOfferDescription(String offerDescription) {
    this.offerDescription = offerDescription;
}

public LocalDate getOfferStarDate() {
    return offerStarDate;
}

public void setOfferStarDate(LocalDate offerStarDate) {
    this.offerStarDate = offerStarDate;
}

public LocalDate getOfferEnDate() {
    return offerEnDate;
}

public void setOfferEnDate(LocalDate offerEnDate) {
    this.offerEnDate = offerEnDate;
}

public Auto getAuto() {
    return auto;
}

public void setAuto(Auto auto) {
    this.auto = auto;
}

   

}
