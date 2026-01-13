package auto.auto.service;

import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import auto.auto.model.Rental;
import auto.auto.repository.RentalRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository){
        this.rentalRepository = rentalRepository;
    }

    public double calcolaPrezzoTotale (Rental rent){
        long giorni = ChronoUnit.DAYS.between(rent.getRentStartDate(), rent.getRentEndDate())+1;
        return rent.getAuto().getPrice() * giorni;
    }
    

}
