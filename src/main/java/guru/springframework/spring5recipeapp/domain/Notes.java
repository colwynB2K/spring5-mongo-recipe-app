package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "recipe")
public class Notes {

    private String id;
    private String notes;
    private Recipe recipe;

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", notes='" + notes + '\'' +
                '}';
    }
}
