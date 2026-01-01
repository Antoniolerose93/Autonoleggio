package auto.auto.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="categories")

public class Categories {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_description") 
    private String categoryDescription;

@OneToMany(mappedBy= "category")
    private List <Auto> auto;

public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getCategoryDescription() {
    return categoryDescription;
}

public void setCategoryDescription(String categoryDescription) {
    this.categoryDescription = categoryDescription;
}

public List<Auto> getAuto() {
    return auto;
}

public void setAuto(List<Auto> auto) {
    this.auto = auto;
}
    

}
