package nl.hari.jpa.repo;

import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

    @Modifying
    @Query("update Recipe r set r.name = :name, r.isVegetarian = :isVegetarian," +
            "r.meantForPeople = :meantForPeople, " +
            "r.cookingInstructions = :cookingInstructions  " +
            "where r.name = :oldName")
    int updateRecipe(@Param("name") String name, @Param("cookingInstructions")String cookingInstructions,
                     @Param("isVegetarian") boolean isVegetarian,
                     @Param("meantForPeople") int meantForPeople, @Param("oldName") String oldName);

    @Modifying
    long deleteByName(String recipeName);

}
