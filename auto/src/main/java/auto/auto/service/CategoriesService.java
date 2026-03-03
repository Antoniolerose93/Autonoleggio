package auto.auto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.auto.model.Categories;
import auto.auto.repository.CategoriesRepository;

@Service
public class CategoriesService {


    @Autowired
    private CategoriesRepository categoriesRepository;

    public List<Categories> findAll(){
        return categoriesRepository.findAll();
    }

    public Categories findById(Integer id){
        return categoriesRepository.findById(id)
        .orElseThrow(() ->
        new IllegalArgumentException("Categoria non trovata"));
    }

    public Optional<Categories> findByName(String name){
        return categoriesRepository.findByName(name);
    }

    public Categories createCategory(Categories category){
        Optional<Categories> optExistingCategory = categoriesRepository.findByName(category.getName());
        if(optExistingCategory.isPresent()){
            throw new IllegalArgumentException("Questa categoria è già esistente");
        }
        return categoriesRepository.save(category);
    }

    public void deleteCategory(Integer id){
        if(!categoriesRepository.existsById(id)){
            throw new IllegalArgumentException("Elemeno non trovato");
        }
    
        categoriesRepository.deleteById(id);

    }

}
