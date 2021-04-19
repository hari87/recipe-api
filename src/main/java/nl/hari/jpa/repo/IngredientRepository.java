package nl.hari.jpa.repo;

import nl.hari.jpa.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredients, Long> {
}
