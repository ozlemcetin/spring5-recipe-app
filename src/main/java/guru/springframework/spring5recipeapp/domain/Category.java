package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/*
    Bidirectional relationships create circular references.
    Use @EqualsAndHashCode(exclude = {"recipes"}) to resolve
 */
@Data
@EqualsAndHashCode(exclude = {"recipes"})
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
