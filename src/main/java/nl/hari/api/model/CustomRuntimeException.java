package nl.hari.api.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomRuntimeException extends RuntimeException{
    private final int errorCode;
    private final String errorDescription;
}
