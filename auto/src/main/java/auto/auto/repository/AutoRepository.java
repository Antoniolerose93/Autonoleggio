package auto.auto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Auto;

public interface AutoRepository extends JpaRepository<Auto, Integer> {

    List<Auto> findByModelIgnoreCase(String model);

    List<Auto> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String brand, String model);

    List<Auto> findByBrandIgnoreCase(String brand);

    Optional<Auto> findByModel(String model);

    Optional<Auto> findByTargaIgnoreCase(String targa);

    List <Auto> findByBrandAndFuel(String brand, String fuel);

    List<Auto> findByFuel(String fuel);

}
