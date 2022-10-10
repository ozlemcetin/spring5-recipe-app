package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(exclude = {"unitOfMeasure", "recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;


        /*
        ===
         */

        /*
            We don't need a bidirectional relationship.
            This is a unidirectional relationship from Ingredient to UnitOfMeasure.
            We DO NOT cascade persistence events form Ingredient to UnitOfMeasure.
         */

    /*
        @OneToOne is by default is EAGER.
        This is just an example to show how to explicitly set a Fetch Type
     */
    @OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "measure_id")
    private UnitOfMeasure unitOfMeasure;

    /*
        We don't do any cascading here.
        If we delete an ingredient we don't want to cascade the delete operation to the recipe
     */
    @ManyToOne
    //@JoinColumn(name = "recipe_id")
    private Recipe recipe;

        /*
        ===
         */

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.recipe = recipe;
    }

}
