package nl.hari;

import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
public class JpaTests {
    public static final String RECIPE_NAME = "ginger-bread";

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    @Transactional
    public void updateRecipeInfo(){
        int ifUpdateReturnValueIs1ThenUpdateIsSuccess = 1;
        givenRecipeDataIsPopulatedUsingInsert();
        int updateStatusValue = whenMeantForPeopleInRecipeDataIsUpdated();
        //then
        assert (ifUpdateReturnValueIs1ThenUpdateIsSuccess == updateStatusValue);
    }

    private int whenMeantForPeopleInRecipeDataIsUpdated() {
        Recipe recipe = recipeRepository.findByName(RECIPE_NAME).get();
        recipe.setMeantForPeople(5);
        int updateStatus = recipeRepository.updateRecipe(recipe.getName(), "updated cooking instructions",
        recipe.isVegetarian(),recipe.getMeantForPeople(),RECIPE_NAME);
        return updateStatus;
    }

    private Recipe prepareRecipeDataWithIngredientInfo(){
        Ingredients ingredients = new Ingredients();
        ingredients.setIngredient_name("ginger");
        List<Ingredients> ingredientsList = new ArrayList<>();
        ingredientsList.add(ingredients);
        Recipe recipe = new Recipe(RECIPE_NAME, new java.sql.Date(Calendar.getInstance().getTimeInMillis()),
                true, 4, ingredientsList, "instructions");
        return recipe;
    }

    private void givenRecipeDataIsPopulatedUsingInsert() {
        recipeRepository.save(prepareRecipeDataWithIngredientInfo());
    }

}
