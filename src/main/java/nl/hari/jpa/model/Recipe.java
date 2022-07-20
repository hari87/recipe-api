package nl.hari.jpa.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "recipe", schema = "public")
public class Recipe implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "recipe_id", sequenceName = "recipe_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id")
    @Column(name = "recipe_id")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "creation_date")
    private Date createdDate;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    @Column(name = "meant_for_people")
    private int meantForPeople;

    @Column(name = "cooking_instructions")
    private String cookingInstructions;

    @OneToMany(targetEntity=Ingredients.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    private List<Ingredients> ingredients;

    public Recipe(String name, Date createdDate, boolean vegetarian, int meantForPeople, List<Ingredients> ingredients, String cookingInstructions) {
        this.name = name;
        this.createdDate = createdDate;
        this.vegetarian = vegetarian;
        this.meantForPeople = meantForPeople;
        this.ingredients = ingredients;
        this.cookingInstructions = cookingInstructions;
    }
}