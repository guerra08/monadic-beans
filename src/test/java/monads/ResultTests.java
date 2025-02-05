package monads;

import com.guerra08.monads.Result;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResultTests {

    @Test
    void error_shouldBuildErrorResult() {
        Result<String> result = Result.error(new IllegalArgumentException("An error happened."));

        assertTrue(result.isError());
    }

    @Test
    void ok_shouldBuildOkResult() {
        Result<String> result = Result.ok("Ok!");

        assertTrue(result.isOk());
    }

    @Test
    void match_shouldMatchToString() {
        Result<String> result = Result.ok("Ok!");

        String value = result.match(
                ok -> ok,
                Throwable::getMessage
        );

        assertEquals("Ok!", value);
    }

    @Test
    void of_shouldReturnOkFromSuccessfulSupplier() {
        Result<String> result = Result.of(() -> "Hello there!");

        assertTrue(result.isOk());
    }

    @Test
    void of_shouldReturnErrorFromFailedSupplier() {
        Result<Integer> result = Result.of(() -> { throw new RuntimeException(); });

        assertTrue(result.isError());
    }

    // Test cases for map()
    @Test
    void testMapOnOk() {
        // Arrange
        Result<Integer> okResult = Result.ok(42);

        // Act
        Result<String> mappedResult = okResult.map(Object::toString);

        // Assert
        assertTrue(mappedResult.isOk());
        assertEquals("42", ((Result.Ok<String>) mappedResult).value());
    }

    @Test
    void testMapOnError() {
        // Arrange
        Throwable error = new RuntimeException("Error occurred");
        Result<Integer> errorResult = Result.error(error);

        // Act
        Result<String> mappedResult = errorResult.map(Object::toString);

        // Assert
        assertTrue(mappedResult.isError());
        assertEquals(error, ((Result.Error<String>) mappedResult).t());
    }

    @Test
    void testMapWithFunctionThatThrowsException() {
        // Arrange
        Result<Integer> okResult = Result.ok(10);

        // Act
        Result<String> mappedResult = okResult.map(val -> {
            throw new RuntimeException("Mapping error");
        });

        // Assert
        assertTrue(mappedResult.isError());
        assertEquals("Mapping error", ((Result.Error<String>) mappedResult).t().getMessage());
    }

    // Test cases for recover()
    @Test
    void testRecoverOnOk() {
        // Arrange
        Result<Integer> okResult = Result.ok(42);

        // Act
        Integer recoveredValue = okResult.recover(throwable -> 0);

        // Assert
        assertEquals(42, recoveredValue); // No recovery is needed
    }

    @Test
    void testRecoverOnError() {
        // Arrange
        Throwable error = new RuntimeException("Something went wrong");
        Result<Integer> errorResult = Result.error(error);

        // Act
        Integer recoveredValue = errorResult.recover(throwable -> 0);

        // Assert
        assertEquals(0, recoveredValue); // Recovery is applied
    }

    @Test
    void testRecoverWithFunctionThatReadsThrowable() {
        // Arrange
        Throwable error = new IllegalArgumentException("Invalid input");
        Result<Integer> errorResult = Result.error(error);

        // Act
        Integer recoveredValue = errorResult.recover(throwable -> {
            if (throwable instanceof IllegalArgumentException) {
                return 1; // Recovery for specific exception
            }
            return 0;
        });

        // Assert
        assertEquals(1, recoveredValue);
    }

    @Test
    void testRecoverOnOkDoesNotInvokeRecoveryFunction() {
        // Arrange
        Result<Integer> okResult = Result.ok(42);

        // Act
        Integer recoveredValue = okResult.recover(throwable -> {
            throw new RuntimeException("This should not be invoked");
        });

        // Assert
        assertEquals(42, recoveredValue);
    }

    // Combined test for chaining (map + recover)
    @Test
    void testMapAndRecoverCombined() {
        // Arrange
        Result<Integer> errorResult = Result.error(new RuntimeException("Initial error"));

        // Act
        String finalResult = errorResult
                .map(Object::toString) // Should not map because it's an Error
                .recover(throwable -> "Fallback value"); // Recovery applies

        // Assert
        assertEquals("Fallback value", finalResult);
    }

    // Map test where mapping would introduce a new error
    @Test
    void testMapOnOkToErrorThroughMappingFunction() throws IllegalArgumentException {
        // Arrange
        Result<Integer> okResult = Result.ok(100);

        // Act
        Result<Integer> result = okResult.map(value -> {
            if (value > 50) {
                throw new IllegalArgumentException("Value too high");
            }
            return value / 2;
        });

        // Assert
        assertTrue(result.isError());
        assertEquals("Value too high", ((Result.Error<Integer>) result).t().getMessage());
    }

}
