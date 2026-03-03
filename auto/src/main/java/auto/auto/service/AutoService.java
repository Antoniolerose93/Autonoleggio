package auto.auto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.auto.model.Auto;
import auto.auto.repository.AutoRepository;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    public List<Auto> findByKeyword(String keyword){
        if(keyword == null || keyword.isBlank()){
            return autoRepository.findAll();    
        }
        return autoRepository.findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(keyword, keyword);
    }

    public Auto findById(Integer id){
        return autoRepository.findById(id)
        .orElseThrow(() -> 
        new IllegalArgumentException("Auto non trovata"));    
    }

    public List<Auto> findAll(){
        return autoRepository.findAll();
    }

    public List<Auto> searchAuto(String brand, String model, String fuel){
        return autoRepository.findAll().stream()
        .filter(auto -> (brand == null || brand.isEmpty() || auto.getBrand().equals(brand)))
        .filter(auto -> (model == null || model.isEmpty() || auto.getModel().equals(model)))
        .filter(auto -> (fuel == null || fuel.isEmpty() || auto.getFuel().equals(fuel)))
        .toList();
    }

    public List<String> findAllBrands(){
        return autoRepository.findAll().stream()
        .map(Auto::getBrand)
        .distinct()
        .sorted()
        .toList();
    }


    public List<String> findModelsByBrand(String brand){

        if(brand == null || brand.isEmpty()) return new ArrayList<>();
        return autoRepository.findAll().stream()
        .filter (auto -> auto.getBrand().equals(brand))
        .map(Auto::getModel)
        .distinct()
        .sorted()
        .toList();

    }

    public List <String> findFuelsByBrandAndModel(String brand, String model){
        if(brand == null || model == null || brand.isEmpty() || model.isEmpty()) return new ArrayList<>();
        return autoRepository.findAll().stream()
        .filter(auto -> auto.getBrand().equals(brand) && auto.getModel().equals(model))
        .map(Auto::getFuel)
        .distinct()
        .sorted()
        .toList();
    }


    public Auto createAuto(Auto auto){
        Optional<Auto> existingAuto = autoRepository.findByTargaIgnoreCase(
            auto.getTarga()
        );
        if(existingAuto.isPresent()){
            throw new IllegalArgumentException("Auto già esistente");
        }
        return autoRepository.save(auto);
    }

    public Auto editAuto(Integer id, Auto formAuto){
        Auto existingAuto = autoRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Auto non trovata"));
        
        if (!existingAuto.getBrand().equals(formAuto.getBrand())) {
        throw new IllegalArgumentException("Impossibile modificare il brand dell'auto!");
    }
        
        existingAuto.setDescription(formAuto.getDescription());
        existingAuto.setFoto(formAuto.getFoto());
        existingAuto.setPrice(formAuto.getPrice());

        return autoRepository.save(existingAuto);

    }

    public void delete(Integer id){
        if(!autoRepository.existsById(id)){
            throw new IllegalArgumentException("Auto non trovata");
        }
        autoRepository.deleteById(id);
    }



}
