package guru.springframework.spring5recipeapp.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    /*
    JPA gets a CLOB filed in the database to persits the notes w/o worrying about limitations on the size of the String.
     */
    @Lob
    private String recipeNotes;

   /*
    Getters and Setters
    */
}
