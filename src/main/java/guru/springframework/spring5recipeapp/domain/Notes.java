package guru.springframework.spring5recipeapp.domain;

import lombok.Data;

import javax.persistence.*;

@Data
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


}
