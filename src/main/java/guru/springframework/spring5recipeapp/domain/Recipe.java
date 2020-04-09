package guru.springframework.spring5recipeapp.domain;

import javax.persistence.*;
import java.util.Set;

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
    private String directions;

    /*
    cascade: Recipe owns this.
    mappedBy: property on the child class.
    mappedBy = "recipe", target property on the Ingredient class
    This Recipe will get stored on a property recipe on the child
    Bi-directional relationship
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

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



    /*
    Getters and Setters
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}