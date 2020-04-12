package guru.springframework.spring5recipeapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    /*
    Uni-directional relationship from  Ingredient to  UnitOfMeasure
    Do not cascade persistence events from  Ingredient to  UnitOfMeasure.
    UnitOfMeasure is a reference table

    (fetch = FetchType.EAGER) id the default behavior
     */
    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    @ManyToOne
    private Recipe recipe;

    /*
    Constructors
     */
    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

    /*
    Getters and Setters
     */
}
