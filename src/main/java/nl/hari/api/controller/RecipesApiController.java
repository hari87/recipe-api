package nl.hari.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import nl.hari.api.RecipesApi;
import nl.hari.api.model.CustomRuntimeException;
import nl.hari.api.model.DefaultSuccess;
import nl.hari.api.model.RecipeInfo;
import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.repo.RecipeRepository;
import nl.hari.service.RecipeFilterService;
import nl.hari.transform.ApiToJpa;
import nl.hari.transform.JpaToApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-17T15:23:26.974+02:00")
@Log4j2
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

    private RecipeFilterService recipeFilterService;

    @Autowired
    public RecipesApiController(ObjectMapper objectMapper, HttpServletRequest request, RecipeFilterService recipeFilterService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.recipeFilterService = recipeFilterService;
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
    public ResponseEntity<List<RecipeInfo>> filterRecipes(String expectedIngredient, String avoidIngredient, Boolean onlyVegetarian, String containingInstructions, Integer meantForPeople) {
        /**
         * Instead of approach of having huge method signature, I would have preferred this https://stackoverflow.com/a/18489124/2763492.
         * Since it was a contract first approach I did not intend to modify the Interface for further extendability.
         * More Note: I do not write comments such as these, and usually put in PR comments.
         */
        Map<String, Object> filterParamMap = new HashMap<>();
        filterParamMap.put("expected-ingredient", expectedIngredient);
        filterParamMap.put("avoid-ingredient", avoidIngredient);
        filterParamMap.put("vegetarian", onlyVegetarian);
        filterParamMap.put("cookingInstructions", containingInstructions);
        filterParamMap.put("meantForPeople", meantForPeople);
        log.info("Recived GET request with following filters ", filterParamMap);
        Specification spec = recipeFilterService.constructRecipeQueries(filterParamMap);
        try {
            if (spec == null) {
                return constructEntityWithTransform(recipeRepository.findAll());
            } else {
                return constructEntityWithTransform(recipeRepository.findAll(spec));
            }
        }
        catch (Exception e){
            log.error("An exception occurred while getting/transforming recipe ", e);
            throw e;
        }
    }

    @Transactional
    private ResponseEntity<List<RecipeInfo>> constructEntityWithTransform(List<Recipe> getAllAvailableRecipesInDB) {
        List<RecipeInfo> recipeInfos = getAllAvailableRecipesInDB.stream()
                .map(recipe -> jpaToApi.transformDBReturnedRecipeToApiRecipeInfoObject(recipe)).collect(Collectors.toList());
        return new ResponseEntity<>(recipeInfos, HttpStatus.OK);
    }


        @Override
        @Transactional
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
