package guru.springframework.spring5recipeapp.controller;

import guru.springframework.spring5recipeapp.dto.IngredientDTO;
import guru.springframework.spring5recipeapp.dto.RecipeDTO;
import guru.springframework.spring5recipeapp.dto.UnitOfMeasureDTO;
import guru.springframework.spring5recipeapp.service.IngredientService;
import guru.springframework.spring5recipeapp.service.RecipeService;
import guru.springframework.spring5recipeapp.service.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService mockIngredientService;

    @Mock
    private RecipeService mockRecipeService;

    @Mock
    private UnitOfMeasureService mockUnitOfMeasureService;

    @InjectMocks
    private IngredientController ingredientController;

    private MockMvc mockMvc;

    private String recipeId = "2";
    private Set<UnitOfMeasureDTO> uomList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        uomList = new HashSet<>();
        UnitOfMeasureDTO unitOfMeasureDTO = new UnitOfMeasureDTO();
        uomList.add(unitOfMeasureDTO);
    }

    @Test
    void showListForRecipe() throws Exception {
        // given
        RecipeDTO recipeDTO = new RecipeDTO();


        // when
        when(mockRecipeService.findById(anyString())).thenReturn(recipeDTO);

        // then
        mockMvc.perform(get("/recipes/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("recipe", recipeDTO))
                .andExpect(view().name("recipes/ingredients/list"));
        verify(mockRecipeService).findById(anyString());
    }

    @Test
    void showIngredientForRecipe() throws Exception {
        // given
        IngredientDTO ingredientDTO = new IngredientDTO();

        // when
        when(mockIngredientService.findById(anyString())).thenReturn(ingredientDTO);
        when(mockUnitOfMeasureService.findAll()).thenReturn(uomList);

        // then
        mockMvc.perform(get("/recipes/1/ingredients/2"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ingredient", ingredientDTO))
                .andExpect(model().attribute("uomList", uomList))
                .andExpect(view().name("recipes/ingredients/form"));
        verify(mockIngredientService).findById(anyString());
        verify(mockUnitOfMeasureService).findAll();
    }

    @Test
    void showNewIngredientForm() throws Exception {
        // given
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setId(recipeId);

        // when
        when(mockUnitOfMeasureService.findAll()).thenReturn(uomList);

        // then
        mockMvc.perform(get("/recipes/1/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("uomList", uomList))
                .andExpect(view().name("recipes/ingredients/form"));
        verify(mockUnitOfMeasureService).findAll();
    }

    @Test
    void save() throws Exception {
        // given
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setId("3");

        // when
        when(mockIngredientService.saveIngredientOnRecipe(anyString(), any())).thenReturn(ingredientDTO);

        // then
        mockMvc.perform(post("/recipes/2/ingredients")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "3")
                        .param("name", "Eye of noot")
                    ).andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/recipes/2/ingredients/3"));
    }

    @Test
    void deleteIngredientFromRecipe() throws Exception {
        mockMvc.perform(get("/recipes/2/ingredients/8/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/2/ingredients"));
        verify(mockIngredientService).deleteById(anyString());
    }
}