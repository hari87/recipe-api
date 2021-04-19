package nl.hari.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hari.api.RecipesApi;
import nl.hari.api.model.CustomRuntimeException;
import nl.hari.api.model.DefaultSuccess;
import nl.hari.api.model.ErrorMessage;
import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import nl.hari.transform.ApiToJpa;
import nl.hari.transform.JpaToApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-17T15:23:26.974+02:00")

@Controller
public class RecipesApiController implements RecipesApi {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    JpaToApi jpaToApi;

    @Autowired
    ApiToJpa apiToJpa;

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public RecipesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public Optional<ObjectMapper> getObjectMapper() {
        return Optional.ofNullable(objectMapper);
    }

    @Override
    public Optional<HttpServletRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<RecipeInfo>> recipesGet(){
        List<Recipe> getAllAvailableRecipesInDB = recipeRepository.findAll();
        List<RecipeInfo> recipeInfos = getAllAvailableRecipesInDB.stream()
                .map(recipe -> jpaToApi.transformDBReturnedRecipeToApiRecipeInfoObject(recipe)).collect(Collectors.toList());
        return new ResponseEntity<>(recipeInfos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DefaultSuccess> recipesPost(RecipeInfo recipeInfo){
        try {
            Recipe recipeDbObject = apiToJpa.whenTranslateRecipeInfoApiObjectToRecipeDbObject(recipeInfo);
            recipeRepository.save(recipeDbObject);
            return new ResponseEntity<>(jpaToApi.prepare200Success(), HttpStatus.CREATED);
        }
        catch (Exception E){
            throw new CustomRuntimeException(500, "An exception occurred while persisting data");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<DefaultSuccess> recipesPut(String currentRecipeName, RecipeInfo recipeInfo){
            Recipe recipeDbObject = apiToJpa.recipeApiToRecipeDbObject(recipeInfo);
            recipeRepository.updateRecipe(recipeInfo.getRecipeName(), recipeInfo.getCookingInstructions(),
                    recipeInfo.isIsVegetarian(), recipeInfo.getMeantForHowManyPeople(),
                    currentRecipeName);
            return new ResponseEntity<>(jpaToApi.prepare200Success(), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<DefaultSuccess> recipesDelete(String recipeName){

            recipeRepository.deleteByName(recipeName);
            return new ResponseEntity<>(jpaToApi.prepare200Success(), HttpStatus.OK);


    }
}
