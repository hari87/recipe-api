package nl.hari;

import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import nl.hari.transform.ApiToJpa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableTransactionManagement
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
