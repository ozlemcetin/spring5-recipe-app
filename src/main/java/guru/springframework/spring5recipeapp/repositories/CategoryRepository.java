package guru.springframework.spring5recipeapp.repositories;

import guru.springframework.spring5recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    /*
    Create a method name using a property using find, find by property name.
    Spring Data JPA is going to go in, create that query based on the criteria found inside the method name.
    This is  feature of Spring Data JPA, it saves us a lot of work and time.
     */
    Optional<Category> findByDescription(String description);
}
