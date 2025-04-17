package com.fullcycle.admin.catalogo.domain.category;

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
         final String expectedName = null;
         final var expectedErrorMessage = "'name' cannot be null";
         final var expectedDescription = "Categoria de filmes";
         final var expectedIsActive = true;

         final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

         final var actualException = Assertions.assertThrows(DomainException.class, () -> category.validate());
         Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).getMessage());
     }
}
