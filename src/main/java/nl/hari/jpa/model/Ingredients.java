package nl.hari.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
@Getter @Setter
@Entity
@Table(name = "ingredients", schema = "public")
public class Ingredients {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ingredient_id", sequenceName = "ingredient_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id")
    @Column(name="ingredient_id")
    private Integer id;

    @Column(name = "ingredient_name")
    private String ingredient_name;

    public Ingredients(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public Ingredients() {
    }
}
