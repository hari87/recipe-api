package nl.hari.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class RecipeFilterServiceTest {

    @InjectMocks
    private RecipeFilterService recipeFilterService;

    @Test
    public void constructRecipeQueriesTest(){
        Map<String, Object> filterParamMap = new HashMap<>();
        filterParamMap.put("expected-ingredient", "coal");
        filterParamMap.put("vegetarian", false);
        filterParamMap.put("cookingInstructions", "oven");
        filterParamMap.put("meantForPeople", 3);

        Specification spec = recipeFilterService.constructRecipeQueries(filterParamMap);
        assert (spec != null);
    }

    @Test
    public void specificationToReturnNullTest(){
        Map<String, Object> filterParamMap = new HashMap<>();
        Specification spec = recipeFilterService.constructRecipeQueries(filterParamMap);
        assert (spec == null);
    }

}
