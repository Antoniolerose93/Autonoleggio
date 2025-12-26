package auto.auto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    public List<Rental> findByAutoId(Integer autoId);

    public List<Rental> findByDriverId(Integer driverId);

}
