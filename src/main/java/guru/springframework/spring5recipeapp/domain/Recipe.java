package guru.springframework.spring5recipeapp.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private String directions;

    //todo
    //private Difficulty difficulty;

    /*
         @Lob, JPA will expect to store it in a BLOB field in the database
     */
    @Lob
    private Byte[] image;

    /*
    ===
     */

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
    private Set<Ingredient> ingredientSet = new HashSet<>();

    /*
        Recipe owns the Notes.
        When a recipe is deleted, notes are also deleted.
     */
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;


    /*
    ===
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
}