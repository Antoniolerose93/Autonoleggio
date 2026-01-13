package auto.auto.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import auto.auto.model.Rental;
import auto.auto.repository.RentalRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    // Controlla se il periodo scelto è disponibile
    public boolean disponibilita(Rental rent){
        List<Rental> dateSovrapposte = rentalRepository.findByAutoIdAndRentEndDateGreaterThanEqualAndRentStartDateLessThanEqual(
                rent.getAuto().getId(),
                rent.getRentStartDate(),
                rent.getRentEndDate()
            );
        return dateSovrapposte.isEmpty();
    }

    // Restituisce le due date di disponibilità
    public DisponibilitaResidua prossimaDataDisponibile(Integer autoId, LocalDate startDate){
        List<Rental> noleggiFuturi = rentalRepository.findByAutoIdAndRentStartDateGreaterThanEqualOrderByRentStartDateAsc(autoId, startDate);

        if(noleggiFuturi.isEmpty()){
            return new DisponibilitaResidua(startDate, startDate); // Auto libera, stessa data per entrambe
        }

        Rental nextRental = noleggiFuturi.get(0);
        LocalDate disponibileFino = nextRental.getRentStartDate().minusDays(1);
        LocalDate disponibileDa = nextRental.getRentEndDate().plusDays(1);

        return new DisponibilitaResidua(disponibileFino, disponibileDa);
    }

    public double calcolaPrezzoTotale(Rental rent){
        long giorni = ChronoUnit.DAYS.between(rent.getRentStartDate(), rent.getRentEndDate()) + 1;
        return rent.getAuto().getPrice() * giorni;
    }

    // Classe interna per restituire due date
    public static class DisponibilitaResidua {
        private final LocalDate disponibileFino;
        private final LocalDate disponibileDa;

        public DisponibilitaResidua(LocalDate disponibileFino, LocalDate disponibileDa) {
            this.disponibileFino = disponibileFino;
            this.disponibileDa = disponibileDa;
        }

        public LocalDate getDisponibileFino() {
            return disponibileFino;
        }

        public LocalDate getDisponibileDa() {
            return disponibileDa;
        }
    }
}
