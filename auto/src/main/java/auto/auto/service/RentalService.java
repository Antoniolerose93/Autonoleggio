package auto.auto.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.auto.model.Auto;
import auto.auto.model.Rental;
import auto.auto.repository.AutoRepository;
import auto.auto.repository.RentalRepository;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private AutoRepository autoRepository;

    public List<Rental> findAll(){
        return rentalRepository.findAll();
    }


    public Rental findById(Integer id){
        return rentalRepository.findById(id)
        .orElseThrow(()-> 
        new IllegalArgumentException("Elemento non trovato"));
    }


    public List<Rental> findByAuto(Integer autoId) {
        return rentalRepository.findByAutoId(autoId);
    }

    public List<Rental> findByDriver(Integer driverId) {
        return rentalRepository.findByDriverId(driverId);
    }

   
    // Controlla se il periodo scelto è disponibile
    public boolean availability(Rental rent){
        List<Rental> dateSovrapposte = rentalRepository.findByAutoIdAndRentEndDateGreaterThanEqualAndRentStartDateLessThanEqual(
                rent.getAuto().getId(),
                rent.getRentStartDate(),
                rent.getRentEndDate()
            );
        return dateSovrapposte.isEmpty();
    }

    // Restituisce le due date di disponibilità
    public RemainingAvailability nextDateAvailable(Integer autoId, LocalDate startDate){
        List<Rental> futurRentals = rentalRepository.findByAutoIdAndRentStartDateGreaterThanEqualOrderByRentStartDateAsc(autoId, startDate);

        if(futurRentals.isEmpty()){
            return new RemainingAvailability (startDate, startDate); // Auto libera, stessa data per entrambe
        }

        Rental nextRental = futurRentals.get(0);
        LocalDate availableUntil = nextRental.getRentStartDate().minusDays(1);
        LocalDate availableFrom = nextRental.getRentEndDate().plusDays(1);

        return new RemainingAvailability (availableUntil, availableFrom);
    }

    public double calculateTotalPrice(Rental rent){
        long giorni = ChronoUnit.DAYS.between(rent.getRentStartDate(), rent.getRentEndDate()) + 1;
        return rent.getAuto().getPrice() * giorni;
    }

    public String getAvailabilityMessage(Integer autoId, LocalDate startDate) {
        RemainingAvailability availability = nextDateAvailable(autoId, startDate);
        return "Disponibile fino al " + availability.getAvailableUntil() + " e dal " + availability.getAvailableFrom();
    }

    public Rental saveRent(Rental rent){
        Optional<Auto> optAuto = autoRepository.findById(rent.getAuto().getId());
        if(optAuto.isPresent()){
            Auto completeAuto = optAuto.get();
            rent.setAuto(completeAuto);
        } else {
            throw new IllegalArgumentException("Questa auto non esiste");
        }
        if(!availability(rent)){
            throw new IllegalArgumentException("Auto non disponibile nel periodo scelto." + getAvailabilityMessage(rent.getAuto().getId(), LocalDate.now()));
        }

        rent.setTotalPrice(calculateTotalPrice(rent));
        return rentalRepository.save(rent);

    }

      public void delete(Integer id){
        if(!rentalRepository.existsById(id)){
            throw new IllegalArgumentException("Elemento non trovato");
        }
        rentalRepository.deleteById(id);
    }
    // Classe interna per restituire due date
    public static class RemainingAvailability  {
        private final LocalDate availableUntil;
        private final LocalDate availableFrom;

        public RemainingAvailability (LocalDate availableUntil, LocalDate availableFrom) {
            this.availableUntil = availableUntil;
            this.availableFrom = availableFrom;
        }

        public LocalDate getAvailableUntil() {
            return availableUntil;
        }

        public LocalDate getAvailableFrom() {
            return availableFrom;
        }
    }
}
