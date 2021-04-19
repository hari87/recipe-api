package nl.hari.transform;

import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Ingredients;
import nl.hari.jpa.model.Recipe;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApiToJpa {

    public Recipe whenTranslateRecipeInfoApiObjectToRecipeDbObject(RecipeInfo recipeInfo){
        Recipe recipeDbObject =
                new Recipe(recipeInfo.getRecipeName(),
                        new Date(Calendar.getInstance().getTimeInMillis()),
                        recipeInfo.isIsVegetarian(),
                        recipeInfo.getMeantForHowManyPeople(),
                        prepareIngredientsDbObjectFromRecipeInfoApiObject(recipeInfo.getIngredients()),
                        recipeInfo.getCookingInstructions());
        return recipeDbObject;
    }

    private List<Ingredients> prepareIngredientsDbObjectFromRecipeInfoApiObject(List<String> ingredientsAsString){
       return ingredientsAsString.stream().map(item -> new Ingredients(item)).collect(Collectors.toList());
    }

    public Recipe recipeApiToRecipeDbObject(RecipeInfo recipeInfo){
        Recipe recipe = new Recipe();
        recipe.setMeantForPeople(recipeInfo.getMeantForHowManyPeople());
        recipe.setVegetarian(recipeInfo.isIsVegetarian());
        recipe.setName(recipeInfo.getRecipeName());
        recipe.setCookingInstructions(recipeInfo.getCookingInstructions());
        return recipe;
    }

}
