package nl.hari.api.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.validation.annotation.Validated;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 */
@ApiModel(description = "")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-04-17T15:23:26.974+02:00")

public class RecipeInfo   {
  @JsonProperty("creationDate")
  private OffsetDateTime creationDate = null;

  @JsonProperty("isVegetarian")
  private Boolean isVegetarian = null;

  @JsonProperty("meantForHowManyPeople")
  private Integer meantForHowManyPeople = null;

  @JsonProperty("ingredients")
  @Valid
  private List<String> ingredients = new ArrayList<>();

  @JsonProperty("cookingInstructions")
  private String cookingInstructions = null;

  @JsonProperty("recipeName")
  private String recipeName = null;

  public RecipeInfo creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * When the recipe was created
   * @return creationDate
  **/
  @ApiModelProperty(example = "2019-05-17", value = "When the recipe was created")

  @Valid

  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public RecipeInfo isVegetarian(Boolean isVegetarian) {
    this.isVegetarian = isVegetarian;
    return this;
  }

  /**
   * 
   * @return isVegetarian
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isIsVegetarian() {
    return isVegetarian;
  }

  public void setIsVegetarian(Boolean isVegetarian) {
    this.isVegetarian = isVegetarian;
  }

  public RecipeInfo meantForHowManyPeople(Integer meantForHowManyPeople) {
    this.meantForHowManyPeople = meantForHowManyPeople;
    return this;
  }

  /**
   * 
   * @return meantForHowManyPeople
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getMeantForHowManyPeople() {
    return meantForHowManyPeople;
  }

  public void setMeantForHowManyPeople(Integer meantForHowManyPeople) {
    this.meantForHowManyPeople = meantForHowManyPeople;
  }

  public RecipeInfo ingredients(List<String> ingredients) {
    this.ingredients = ingredients;
    return this;
  }

  public RecipeInfo addIngredientsItem(String ingredientsItem) {
    this.ingredients.add(ingredientsItem);
    return this;
  }

  /**
   * 
   * @return ingredients
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public List<String> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
  }

  public RecipeInfo cookingInstructions(String cookingInstructions) {
    this.cookingInstructions = cookingInstructions;
    return this;
  }

  /**
   * 
   * @return cookingInstructions
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCookingInstructions() {
    return cookingInstructions;
  }

  public void setCookingInstructions(String cookingInstructions) {
    this.cookingInstructions = cookingInstructions;
  }

  public RecipeInfo recipeName(String recipeName) {
    this.recipeName = recipeName;
    return this;
  }

  /**
   * name of dish
   * @return recipeName
  **/
  @ApiModelProperty(required = true, value = "name of dish")
  @NotNull


  public String getRecipeName() {
    return recipeName;
  }

  public void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RecipeInfo recipeInfo = (RecipeInfo) o;
    return Objects.equals(this.creationDate, recipeInfo.creationDate) &&
        Objects.equals(this.isVegetarian, recipeInfo.isVegetarian) &&
        Objects.equals(this.meantForHowManyPeople, recipeInfo.meantForHowManyPeople) &&
        Objects.equals(this.ingredients, recipeInfo.ingredients) &&
        Objects.equals(this.cookingInstructions, recipeInfo.cookingInstructions) &&
        Objects.equals(this.recipeName, recipeInfo.recipeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creationDate, isVegetarian, meantForHowManyPeople, ingredients, cookingInstructions, recipeName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RecipeInfo {\n");
    
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    isVegetarian: ").append(toIndentedString(isVegetarian)).append("\n");
    sb.append("    meantForHowManyPeople: ").append(toIndentedString(meantForHowManyPeople)).append("\n");
    sb.append("    ingredients: ").append(toIndentedString(ingredients)).append("\n");
    sb.append("    cookingInstructions: ").append(toIndentedString(cookingInstructions)).append("\n");
    sb.append("    recipeName: ").append(toIndentedString(recipeName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

