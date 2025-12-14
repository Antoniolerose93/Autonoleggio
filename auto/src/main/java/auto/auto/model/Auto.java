package auto.auto.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "auto")

public class Auto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Brand non può essere nullo")
    private String brand;

    private String model;

    private String color;

    private int price;

    private String description;

    private String fuel;

    @NotBlank(message ="url foto obbligatorio")
    private String foto;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories category;

    @OneToOne(mappedBy = "auto")
    private Rental rent;

    @OneToMany(mappedBy="auto")
    private List<Offer> offers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getFoto(){
        return foto;
    }

    public void setFoto(String foto){
        this.foto = foto;
    }

    public Rental getRent() {
        return rent;
    }

    public void setRent(Rental rent) {
        this.rent = rent;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
