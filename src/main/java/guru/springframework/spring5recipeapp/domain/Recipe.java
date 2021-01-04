package guru.springframework.spring5recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"categories", "notes"})
// Excluded categories and notes, but left in recipes for equals and hashCode generation as it seems an essential
// part of the recipe
public class Recipe {

    private String id;
    private String cookInstructions;
    private String description;
    private Integer cookTimeMins;
    private Difficulty difficulty;
    private Byte[] image;
    private String name;
    private Integer prepTimeMins;
    private String source;
    private String url;
    private String yield;

    private Set<Category> categories = new HashSet<>();
    private Set<Ingredient> ingredients = new HashSet<>();
    private Notes notes;

    public Recipe addCategory(Category category) {
        this.categories.add(category);
        category.getRecipes().add(this);

        return this;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);

        return this;
    }

    public Recipe addIngredients(Set<Ingredient> ingredients) {
        ingredients.stream().forEach(ingredient -> ingredient.setRecipe(this));
        this.setIngredients(ingredients);

        return this;
    }

    public Recipe addNotes(Notes notes) {
        this.setNotes(notes);
        notes.setRecipe(this);

        return this;
    }
}
