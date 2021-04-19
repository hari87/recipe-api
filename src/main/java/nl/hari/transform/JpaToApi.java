package nl.hari.transform;

import nl.hari.api.model.DefaultSuccess;
import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaToApi {

    public RecipeInfo transformDBReturnedRecipeToApiRecipeInfoObject(Recipe dbRecipe){
        RecipeInfo recipeInfo = new RecipeInfo();
        recipeInfo.setIsVegetarian(dbRecipe.isVegetarian());
        recipeInfo.setRecipeName(dbRecipe.getName());
        recipeInfo.setCookingInstructions(dbRecipe.getCookingInstructions());
        recipeInfo.setMeantForHowManyPeople(dbRecipe.getMeantForPeople());
        recipeInfo.setIngredients(getAllIngredientsAsListFromDbIngredientsObject(dbRecipe.getIngredients()));
        recipeInfo.creationDate(transformSqlDateToOffsetDateTime(dbRecipe.getCreatedDate()));
        return recipeInfo;
    }
    public List<String> getAllIngredientsAsListFromDbIngredientsObject(List<Ingredients> dbIngredientsObject){
      return  dbIngredientsObject.stream().map(ingredients -> ingredients.getIngredient_name()).collect(Collectors.toList());
    }

    public OffsetDateTime transformSqlDateToOffsetDateTime(java.sql.Date sqlDate){
        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(sqlDate.getTime()), ZoneId.systemDefault());
        ZoneOffset offset = ZoneOffset.UTC;
        return date.atOffset(offset);
    }

    public static DefaultSuccess prepare200Success(){
        DefaultSuccess defaultSuccess = new DefaultSuccess();
        defaultSuccess.setStatus("processed successfully");
        return defaultSuccess;
    }

}
