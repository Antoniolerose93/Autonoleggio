package auto.auto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.auto.model.Drivers;
import auto.auto.repository.DriversRepository;

@Service
public class DriverService {

    @Autowired
    private  DriversRepository driversRepository;

    public Drivers getOrCreateDrivers(Drivers driverForm) {
        return driversRepository.findByDrivingLicense(driverForm.getDrivingLicense())
                .orElse(driverForm);

    }

    public Drivers findById(Integer id){
        return driversRepository.findById(id)
        .orElseThrow(()-> 
        new IllegalArgumentException("Elemento non trovato"));
    }

    public List<Drivers> findAll(){
        return driversRepository.findAll();
    }

    public void saveDriver(Drivers driver){
        driversRepository.save(driver);
    }

}
