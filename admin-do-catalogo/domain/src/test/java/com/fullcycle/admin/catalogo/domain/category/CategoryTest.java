package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
 class CategoryTest {

     @Test
     void givenAValidParams_whenCallANewCategory_thenInstantiateACategory() {
         final var expectedName = "Filmes";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         Assertions.assertNotNull(category);
         Assertions.assertNotNull(category.getId());
         Assertions.assertEquals(expectedName, category.getName());
         Assertions.assertEquals(expectedDescription, category.getDescription());
         Assertions.assertEquals(expectedIsActive, category.isActive());
         Assertions.assertNotNull(category.getCreatedAt());
         Assertions.assertNotNull(category.getUpdatedAt());
         Assertions.assertNull(category.getDeletedAt());
     }

     @Test
     void givenAnInvalidNullName_whenCallANewCategoryAndValidate_thenShouldReceiveError() {
         final var expectedErrorMessage = "'name' cannot be null";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(null, expectedDescription, expectedIsActive);

         final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
     }

     @Test
     void givenAnInvalidEmptyName_whenCallANewCategoryAndValidate_thenShouldReceiveError() {
         final var expectedName = " ";
         final var expectedErrorMessage = "'name' cannot be empty";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
     }

     @Test
     void givenAnInvalidLessThan3Name_whenCallANewCategoryAndValidate_thenShouldReceiveError() {
         final var expectedName = "Fi ";
         final var expectedErrorMessage = "'name' must have more than 3 characters and less than 255 characters";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
     }

     @Test
     void givenAnInvalidMoreThan255Name_whenCallANewCategoryAndValidate_thenShouldReceiveError() {
         final var expectedName = """
                No entanto, não podemos esquecer que o fenômeno da Internet assume importantes posições no estabelecimento do investimento em reciclagem técnica. 
                A nível organizacional, a execução dos pontos do programa cumpre um papel essencial na formulação das diretrizes de desenvolvimento para o futuro. 
                Gostaria de enfatizar que o aumento do diálogo entre os diferentes setores produtivos nos obriga à análise do processo de comunicação como um todo. 
                Assim mesmo, a percepção das dificuldades exige a precisão e a definição das condições financeiras e administrativas exigidas. 
                No mundo atual, o comprometimento entre as equipes possibilita uma melhor visão global de todos os recursos funcionais envolvidos.
                """;
         final var expectedErrorMessage = "'name' must have more than 3 characters and less than 255 characters";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
     }

     @Test
     void givenAValidEmptyDescription_whenCallANewCategory_thenInstantiateACategory() {
         final var expectedName = "Filmes";
         final var expectedDescription = " ";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertNotNull(category);
         Assertions.assertNotNull(category.getId());
         Assertions.assertEquals(expectedName, category.getName());
         Assertions.assertEquals(expectedDescription, category.getDescription());
         Assertions.assertEquals(expectedIsActive, category.isActive());
         Assertions.assertNotNull(category.getCreatedAt());
         Assertions.assertNotNull(category.getUpdatedAt());
         Assertions.assertNull(category.getDeletedAt());
     }

     @Test
     void givenAValidFalseIsActive_whenCallANewCategory_thenInstantiateACategory() {
         final var expectedName = "Filmes";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = false;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
         Assertions.assertNotNull(category);
         Assertions.assertNotNull(category.getId());
         Assertions.assertEquals(expectedName, category.getName());
         Assertions.assertEquals(expectedDescription, category.getDescription());
         Assertions.assertEquals(expectedIsActive, category.isActive());
         Assertions.assertNotNull(category.getCreatedAt());
         Assertions.assertNotNull(category.getUpdatedAt());
         Assertions.assertNotNull(category.getDeletedAt());
     }

     @Test
     void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactive() {
         final var expectedName = "Filmes";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

         final var updatedAt = aCategory.getUpdatedAt();

         Assertions.assertTrue(aCategory.isActive());
         Assertions.assertNull(aCategory.getDeletedAt());

         final var actualCategory = aCategory.deactivate();

         Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
         Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
         Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
         Assertions.assertFalse(actualCategory.isActive());
         Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
         Assertions.assertNotNull(actualCategory.getDeletedAt());
     }

     @Test
     void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActive() {
         final var expectedName = "Filmes";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = false;

         final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

         final var updatedAt = aCategory.getUpdatedAt();

         Assertions.assertFalse(aCategory.isActive());
         Assertions.assertNotNull(aCategory.getDeletedAt());

         final var actualCategory = aCategory.activate();

         Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
         Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
         Assertions.assertEquals(aCategory.getName(), actualCategory.getName());
         Assertions.assertEquals(aCategory.getDescription(), actualCategory.getDescription());
         Assertions.assertTrue(actualCategory.isActive());
         Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
         Assertions.assertNull(actualCategory.getDeletedAt());
     }

}
