package guru.springframework.spring5recipeapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    /*
    ===
     */

    /*
        categories is a property on the Recipe class.
        On the Recipe side, we define a Join Table.
     */
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes = new HashSet<>();


}
