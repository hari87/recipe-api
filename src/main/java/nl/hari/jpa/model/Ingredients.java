package nl.hari.jpa.model;

import lombok.*;

import javax.persistence.*;
@Getter @Setter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "ingredients", schema = "public")
public class Ingredients {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ingredient_id", sequenceName = "ingredient_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id")
    @Column(name="ingredient_id")
    private Integer id;

    @Column(name = "ingredient_name")
    private String ingredientName;

    public Ingredients(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Ingredients() {
    }
}
