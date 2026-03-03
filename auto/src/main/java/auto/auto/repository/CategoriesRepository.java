package auto.auto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Categories;


public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    Optional<Categories> findByName(String name);

}
