package guru.springframework.spring5recipeapp.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class UnitOfMeasure {

    /*
        This is a reference table to define the different measures for recipes, ingredients.
        Adding a new UnitOfMeasure through database, rather than hard-coding them like enums, is a decision.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;


}
