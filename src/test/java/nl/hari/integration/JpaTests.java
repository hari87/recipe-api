package nl.hari.integration;

import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import nl.hari.jpa.specification.RecipeSpecification;
import nl.hari.jpa.specification.SearchCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
    public void specificationFilterTest(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        RecipeSpecification spec = new RecipeSpecification(new SearchCriteria("name", "=", "stampot"));
        List<Recipe> recipes =  recipeRepository.findAll(spec);
        assert (recipes.size() == 1);
    }

    @Test
    public void specificationWith2CriteriaFilterTest(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        List<SearchCriteria> params =
                Arrays.asList((new SearchCriteria("name", "=", "stampot")),
                        (new SearchCriteria("meantForPeople", ">", 3)));
        List<Specification> specs = params.stream()
                .map(RecipeSpecification::new)
                .collect(Collectors.toList());
        Specification result = null;
        for (int i = 0; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }

        List<Recipe> recipes =  recipeRepository.findAll(result);
        assert (recipes.size() == 1);
    }

    @Test
    public void specificationFilterWithGreaterThanTest(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        RecipeSpecification spec = new RecipeSpecification(new SearchCriteria("meantForPeople", ">", 2));
        List<Recipe> recipes =  recipeRepository.findAll(spec);
        assert (recipes.size() == 2);
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

    /**
     * I would skip these test cases as they are not used and so are associated methods, but then
     * I learnt JPA specification as I was wondering how can I create dynamic queries to the approach
     * suggested. Thanks.
     */
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
    }

    @Test
    public void getAllRecipes(){
        recipeRepository.save(recipe);
        recipeRepository.save(anotherRecipe);
        List<Recipe> recipes = recipeRepository.findAll();
        assert (recipes.size() == 2);
    }



    @AfterEach
    void cleanUp(){
        recipeRepository.deleteAll();
    }
    
    

}
