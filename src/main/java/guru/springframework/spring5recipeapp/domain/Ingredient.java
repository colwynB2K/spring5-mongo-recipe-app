package guru.springframework.spring5recipeapp.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Ingredient {

    private String id;
    private BigDecimal amount;
    private String name;
    private UnitOfMeasure unitOfMeasure;
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(String name, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.name = name;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Ingredient))
            return false;

        Ingredient other = (Ingredient) o;

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", unitOfMeasure=" + unitOfMeasure +
                '}';
    }
}
