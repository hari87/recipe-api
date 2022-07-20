package nl.hari.service;

import nl.hari.jpa.model.Recipe;
import nl.hari.jpa.specification.RecipeSpecification;
import nl.hari.jpa.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeFilterService {

    public Specification constructRecipeQueries(Map<String, Object> recipeParams){
        recipeParams.values().removeIf(Objects::isNull);
        if(recipeParams.size() == 0){
            return null;
        }
        else {
            List<RecipeSpecification> recipeSpecificationList = recipeParams.keySet().stream()
                    .map(recipeKey -> new RecipeSpecification(new SearchCriteria(
                            getAppropriateColumnValue(recipeKey),
                            deduceSearchCriteria(recipeKey),
                            recipeParams.get(recipeKey))))
                    .collect(Collectors.toList());
            Specification result = recipeSpecificationList.get(0);
            for (int i = 1; i < recipeSpecificationList.size(); i++) {
                result = Specification.where(result)
                        .and(recipeSpecificationList.get(i));
            }
            return result;
        }
    }

    private static String getAppropriateColumnValue(String recipeKey){
        if(recipeKey.contains("-")){
          return  recipeKey.split("-")[1];
        }
        else{
            return recipeKey;
        }
    }



    private static final List<String> recipeColumnNameList(){
        return Arrays.stream(Recipe.class.getFields())
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    private static String deduceSearchCriteria(String recipeParamKey){
        if(recipeParamKey.contains("expected")){
            return "=";
        } else if (recipeParamKey.contains("avoid")) {
            return "!=";
        }
        else {
            return "=";
        }
    }
}
