package nl.hari.integration;

import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class JpaTests {
    public static final String RECIPE_NAME = "ginger-bread";

    @Autowired
    RecipeRepository recipeRepository;

    private Recipe recipe;
    private Recipe anotherRecipe;

    @BeforeEach
    void setup(){
        List<Ingredients> ingredientsList = Arrays.asList(Ingredients.builder()
                .ingredientName("ginger")
                .build());
        recipe = new Recipe(RECIPE_NAME, new Date(Calendar.getInstance().getTimeInMillis()),
                true, 4, ingredientsList, "cook in oven instructions");
        List<Ingredients> ingredientsListForAnotherRecipe =
                Arrays.asList(Ingredients.builder()
                        .ingredientName("potato")
                        .build());

        anotherRecipe = new Recipe("stampot", new Date(Calendar.getInstance().getTimeInMillis()),
                true, 4, ingredientsListForAnotherRecipe, "instructions");
    }

    @Test
    @Transactional
    public void testUpdateRecipeInfo(){
        int ifUpdateReturnValueIs1ThenUpdateIsSuccess = 1;
        recipeRepository.save(recipe);
        recipe.setMeantForPeople(5);
        int updateStatus = recipeRepository.updateRecipe(recipe.getName(), "updated cooking instructions",
                recipe.isVegetarian(),recipe.getMeantForPeople(),RECIPE_NAME);
        assert (ifUpdateReturnValueIs1ThenUpdateIsSuccess == updateStatus);
    }

    @Test
    public void testFindByNameAndVegetarianIsTrue(){
        recipeRepository.save(recipe);
        boolean isRecipeVegetarian = recipeRepository.existsRecipeByNameAndVegetarianIsTrue(RECIPE_NAME);
        assert (isRecipeVegetarian == true);
    }

    @Test
    public void testFindMeantForPeopleByName(){
        recipeRepository.save(recipe);
        int numberOfpeople = recipeRepository.getMeantForPeopleByName(RECIPE_NAME);
        assert (numberOfpeople ==4);
    }
    
    @Test
    public void testFindAllVegetarianRecipes(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        Optional<List<Recipe>> recipes =  recipeRepository.findAllByVegetarianIsTrue();
        assert (recipes.get().size() == 2);

    }
    @Test
    public void testFindAllByIngredientNameAndMeantForPeople(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        Optional<List<Recipe>> recipes =  recipeRepository.findAllByMeantForPeopleAndIngredients_IngredientName(4, "potato");
        assert (recipes.get().size() == 1);
        System.out.println("value is :"+recipes.get().size());

    }

    @Test
    public void testFindAllByCookingInstructionsIsLikeAndIngredients_IngredientNameIsNotContaining(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        Optional<List<Recipe>> recipes =  recipeRepository.findAllByCookingInstructionsIsLikeAndIngredients_IngredientNameIsNotContaining("%oven%", "salmon");
        assert (recipes.get().size() == 1);
        System.out.println("value is :"+recipes.get().size());

    }

    @AfterEach
    void cleanUp(){
        recipeRepository.deleteAll();
    }
    
    

}
