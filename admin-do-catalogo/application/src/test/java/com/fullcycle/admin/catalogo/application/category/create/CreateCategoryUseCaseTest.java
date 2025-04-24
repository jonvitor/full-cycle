package com.fullcycle.admin.catalogo.application.category.create;

import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCategoryUseCaseTest {

    @InjectMocks
    private  DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    void givenAValidCommand_whenCreatingACategory_thenCategoryIsCreated() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description of Category 1";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                    && Objects.equals(expectedDescription, category.getDescription())
                    && Objects.equals(expectedIsActive, category.isActive())
                    && Objects.nonNull(category.getId())
                    && Objects.nonNull(category.getCreatedAt())
                    && Objects.nonNull(category.getUpdatedAt())
                    && Objects.isNull(category.getDeletedAt())
        ));
    }

    @Test
    void givenAInvalidName_whenCallingCreateACategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "Description of Category 1";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' cannot be null";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var exception = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    void givenAValidCommand_whenCreatingAInactiveCategory_thenCategoryIsCreated() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description of Category 1";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getDeletedAt())
        ));
    }

    @Test
    void givenAValidCommand_whenGatewayThrowsRamdomException_thenShouldReturnAException() {
        final var expectedName = "Category 1";
        final var expectedDescription = "Description of Category 1";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenThrow(new RuntimeException(expectedErrorMessage));

        final var exception = Assertions.assertThrows(RuntimeException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());

        Mockito.verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }
}
