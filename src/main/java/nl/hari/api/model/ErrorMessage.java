package nl.hari.api.model;

import java.util.Objects;

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
public class ErrorMessage   {
  @JsonProperty("errorCode")
  private Integer errorCode = null;

  @JsonProperty("errorDescription")
  private String errorDescription = null;

  public ErrorMessage errorCode(Integer errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * Default HTTP error code
   * @return errorCode
  **/
  @ApiModelProperty(required = true, value = "Default HTTP error code")
  @NotNull


  public Integer getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(Integer errorCode) {
    this.errorCode = errorCode;
  }

  public ErrorMessage errorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
    return this;
  }

  /**
   * Provides error message info
   * @return errorDescription
  **/
  @ApiModelProperty(required = true, value = "Provides error message info")
  @NotNull


  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorMessage errorMessage = (ErrorMessage) o;
    return Objects.equals(this.errorCode, errorMessage.errorCode) &&
        Objects.equals(this.errorDescription, errorMessage.errorDescription);
  }

  @Override
  public int hashCode() {
    return Objects.hash(errorCode, errorDescription);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorMessage {\n");
    
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
    sb.append("    errorDescription: ").append(toIndentedString(errorDescription)).append("\n");
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

