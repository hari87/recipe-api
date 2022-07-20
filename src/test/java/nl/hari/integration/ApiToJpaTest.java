package nl.hari.integration;

import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import nl.hari.transform.ApiToJpa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
public class ApiToJpaTest {

    @Autowired
    ApiToJpa apiToJpa;

    @Autowired
    RecipeRepository recipeRepository;

    @Test
    @Transactional
    public void translateRecipeInfoApiObjectToRecipeDbObjectAndPersist(){
        RecipeInfo recipeInfo = givenRecipeInfoObjectIsCreated();
        Recipe recipeDbObject = apiToJpa.whenTranslateRecipeInfoApiObjectToRecipeDbObject(recipeInfo);
        recipeRepository.save(recipeDbObject);
        Recipe recipeDbObjectFromDb = recipeRepository.findByName("test-recipe").get();
        assert (recipeDbObjectFromDb.getName() == "test-recipe");
    }

    private RecipeInfo givenRecipeInfoObjectIsCreated(){
        RecipeInfo recipeInfo = new RecipeInfo();
        recipeInfo.setRecipeName("test-recipe");
        recipeInfo.setIngredients(Arrays.asList("ginger","bread"));
        recipeInfo.setMeantForHowManyPeople(4);
        recipeInfo.setCookingInstructions("call a chef");
        recipeInfo.setIsVegetarian(true);
        return recipeInfo;
    }

}
