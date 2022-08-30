package guru.springframework.spring5recipeapp.domain;

import javax.persistence.*;

@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
        CLOBs character large objects or BLOBs for binary large objects

        Hibernate supports 255 characters for a String.
        To allow the users to put in a lot more than 255 characters on notes fields
        use @Lob annotation.

        JPA will expect to store it in a CLOB field in the database
     */

    @Lob
    private String recipeNotes;

    /*
    ===
     */

    /*
       Notes doesn't own the Recipe.
       When a notes is deleted, a recipe  will remain inside the database.
       We don't have cascade operations happening here.
    */

    @OneToOne
    private Recipe recipe;

    /*
    ===
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeNotes() {
        return recipeNotes;
    }

    public void setRecipeNotes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }
}
