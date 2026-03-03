package auto.auto.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "auto")

public class Auto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Inserisci il brand")
    private String brand;

    @NotBlank(message = "Inserisci il modello")
    private String model;

    @NotBlank(message = "Inserisci il colore")
    private String color;

    @NotBlank(message="Inserisci la targa")
    @Column(unique = true)
    private String targa;

    private int price;

    @NotBlank(message = "Inserisci la descrizione")
    private String description;

    @NotBlank(message = "Inserisci il tipo di alimentazione")
    private String fuel;

    @NotBlank(message ="url foto obbligatorio")
    private String foto;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categories_id", nullable = false)
    private Categories category;

    @OneToMany(mappedBy = "auto")
    private List<Rental> rentals;

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

    public String getTarga(){
        return targa;
    }

    public void setTarga(String targa){
        this.targa = targa;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Categories getCategory(){
        return category;
    }

    public void setCategory(Categories category){
        this.category = category;
    }

}
