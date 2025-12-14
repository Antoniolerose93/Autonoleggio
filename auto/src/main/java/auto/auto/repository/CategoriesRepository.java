package auto.auto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

}
