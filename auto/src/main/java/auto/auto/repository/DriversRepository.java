package auto.auto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Drivers;

public interface DriversRepository extends JpaRepository<Drivers, Integer> {

    List<Drivers> findByNameAndSurnameContainingIgnoreCase(String name, String surname);

    Optional<Drivers> findByDrivingLicense(String drivingLicense);


}
