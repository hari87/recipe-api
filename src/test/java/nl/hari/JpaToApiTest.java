package nl.hari;

import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import nl.hari.transform.JpaToApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaToApiTest {
    public static final String INGREDIENT_NAME = "ginger";
    private static final String COOKING_INSTRUCTIONS = "call a cook";

    @Autowired
    JpaToApi jpaToApi;

    @Test
    public void getAllIngredientsAsListFromDbIngredientsObjectTest() {
        List<Ingredients> givenIngredientsList = givenIngredientDBListObject();
        List<String> whenIngredientsAreExtractedAsList = jpaToApi.getAllIngredientsAsListFromDbIngredientsObject(givenIngredientsList);
        //then
        assert (whenIngredientsAreExtractedAsList.size() == 1);
        assert (whenIngredientsAreExtractedAsList.get(0) == INGREDIENT_NAME);
    }

    @Test
    public void translateRecipeListDBModelToRecipeInfoListApiModel() {
        List<Recipe> givenRecipeDBListObject = givenRecipeDBListObject();
        List<RecipeInfo> whenRecipeDBListIsTranslatedToRecipeInfoList = givenRecipeDBListObject.stream()
                .map(recipe -> jpaToApi.transformDBReturnedRecipeToApiRecipeInfoObject(recipe)).collect(Collectors.toList());
        //then
        assert (whenRecipeDBListIsTranslatedToRecipeInfoList.get(0).getCookingInstructions() == COOKING_INSTRUCTIONS);
    }

    @Test
    public void dateTransformationTest(){
        Date sqlDate = new Date(Calendar.getInstance().getTimeInMillis());
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(sqlDate.getTime()), ZoneId.systemDefault());
        ZoneOffset offset = ZoneOffset.UTC;
        OffsetDateTime odt = date.atOffset(offset);
        assert (odt.getClass().getName() == "java.time.OffsetDateTime");
    }

    private List<Recipe> givenRecipeDBListObject() {
        List<Recipe> recipeDBObject = new ArrayList<>();
        List<Ingredients> ingredientsDBList = new ArrayList<>();
        ingredientsDBList.add(prepareIngredientObject(INGREDIENT_NAME, 1));
        ingredientsDBList.add(prepareIngredientObject("AnotherIngredient", 2));
        Recipe recipe = new Recipe(INGREDIENT_NAME, new Date(Calendar.getInstance().getTimeInMillis()), true, 4, ingredientsDBList, COOKING_INSTRUCTIONS);
        recipeDBObject.add(recipe);
        return recipeDBObject;
    }

    private List<Ingredients> givenIngredientDBListObject() {
        List<Ingredients> ingredientsList = new ArrayList<Ingredients>();
        ingredientsList.add(prepareIngredientObject(INGREDIENT_NAME, 1));
        return ingredientsList;
    }

    private Ingredients prepareIngredientObject(String name, int id) {
        Ingredients ingredient1 = new Ingredients();
        ingredient1.setIngredient_name(name);
        ingredient1.setId(id);
        return ingredient1;
    }

}

