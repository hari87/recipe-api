package nl.hari.jpa.specification;

import nl.hari.jpa.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class RecipeSpecification implements Specification<Recipe> {

    final private SearchCriteria criteria;

    public RecipeSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        switch (criteria.getOperation()) {
            case ":":
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return builder.like(
                            root.get(criteria.getKey()),
                            "%" + criteria.getValue() + "%");
                } else {
                    return builder.equal(root.get(criteria.getKey()),
                            criteria.getValue());
                }
            case "=":
                return builder.equal(root.get(criteria.getKey()),
                        criteria.getValue());
            case ">":
                return builder.greaterThan(root.get(criteria.getKey()), (int) criteria.getValue());

            case "<":
                return builder.lessThan(root.get(criteria.getKey()), (int) criteria.getValue());

            case "!=":
                return builder.notEqual(root.get(criteria.getKey()),  criteria.getValue());

            case "CONTAINS":
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            default:
                return null;
        }
    }
}
