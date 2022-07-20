package nl.hari.jpa.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class SearchCriteria {

    private final String key;
    private final String operation;
    private final Object value;


}
