package auto.auto.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import auto.auto.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    List<Rental> findByAutoId(Integer autoId);

    List<Rental> findByDriverId(Integer driverId);

    Optional<Rental> findTopByAutoIdAndRentEndDateAfterOrderByRentEndDateDesc(Integer autoId, LocalDate currentDate);

    List<Rental> findByAutoIdAndRentEndDateGreaterThanEqualAndRentStartDateLessThanEqual(Integer autoId, LocalDate startDate, LocalDate endDate);

    List<Rental> findByAutoIdAndRentStartDateGreaterThanEqualOrderByRentStartDateAsc(Integer autoId, LocalDate startDate);

}
