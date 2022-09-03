package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"notes", "ingredients", "categories"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    /*
         Value too long for column "DIRECTIONS CHARACTER VARYING(255)": "U&'1 Cut avocado, remove flesh:
         Cut the avocados in half. Remove seed. Score the... (1303)"; SQL statement:

         Use @Lob Annotation for directions to add more than 255 characters
     */
    @Lob
    private String directions;

    /*

        //EASY, MODERATE, HARD

        @Enumerated(value = EnumType.ORDINAL)
        @Enumerated(value = EnumType.STRING)
        Defines how it gets persisted in the database.
        ORDINAL is the default; it will get persisted as 1, 2, and 3.

        If we are to add a new enum,
        //EASY, MODERATE, HARD_ISH, HARD
        The value of HARD just changes from 3 to 4.

        Chose to use the string value, because the enums ordinal positions may get changed
     */
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    /*
         @Lob, JPA will expect to store it in a BLOB field in the database
     */
    @Lob
    private Byte[] image;

    /*
    ===
     */

    /*
      Recipe owns the Notes.
      When a recipe is deleted, notes are also deleted.
   */
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

     /*
        @OneToMany Relationship;
        The Recipe will have many Ingredients
        while an Ingredient will have just one Recipe.
     */

    /*
       Recipe owns the Ingredient.
       For mappedBy, use the target property on the child class.
       This Recipe will get stored on the child object
       The Ingredient has a property called recipe.
       This completes from Recipe to Ingredient side of the relationship.
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    /*
        For @ManyToMany Relationships specify a common JOIN TABLE with @JoinTable.
        On the other side, we need to tell it   @ManyToMany(mappedBy = "categories")
     */

    @ManyToMany
    @JoinTable(name = "recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    /*
    ==
     */

    public Recipe addIngredient(Ingredient ingredient) {

        //JPA Bidirectional Relationships
        if (ingredient != null) ingredient.setRecipe(this);

        this.ingredients.add(ingredient);
        return this;
    }


    public void setNotes(Notes notes) {

        //JPA Bidirectional Relationships
        if (notes != null) notes.setRecipe(this);

        this.notes = notes;
    }

}
