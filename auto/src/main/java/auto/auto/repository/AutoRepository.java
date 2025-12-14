package auto.auto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Auto;

public interface AutoRepository extends JpaRepository<Auto, Integer> {

    public List<Auto> findBymodelContainingIgnoreCase(String model);

    public List<Auto> findBybrandIgnoreCase(String brand);

    public Optional<Auto> findByModel(String model);

}
