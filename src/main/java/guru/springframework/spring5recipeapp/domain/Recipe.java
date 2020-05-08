package guru.springframework.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode(exclude = {"ingredients", "notes", "categories"})
//@ToString(exclude = {"ingredients", "notes", "categories"})
@Entity
public class Recipe {

    /*
    IDENTITY supports automatic generation of a sequence.
    Strategy gets the ID from the database upon persistence.
     */
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
    If Lob is not be used, Hibernate would have created a default filed of 255 characters
     */
    @Lob
    private String directions;

    /*
    cascade: Recipe owns this.
    mappedBy: property on the child class.
    mappedBy = "recipe", target property on the Ingredient class
    This Recipe will get stored on a property recipe on the child
    Bi-directional relationship
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    /*
   This will get created as a binary large object field, BLOB inside the database
    */
    @Lob
    private Byte[] image;

    /*
    ORDINAL is the default value (1, 2, 3). Use STRING instead (EASY, HARD etc.)
     */
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    /*
    One to One property.
    Use Cascade to make the recipe the owner.
    If we delete the recipe, the notes will be deleted automatically too.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    /*
    Getters and Setters
     */


    /*
    For Bi-Directional Relationship Management
     */

    public void setNotes(Notes notes) {

        this.notes = notes;

        if (notes != null) {
            notes.setRecipe(this);
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {

        this.ingredients.add(ingredient);

        if (ingredient != null) {
            ingredient.setRecipe(this);
        }

        return this;
    }


}