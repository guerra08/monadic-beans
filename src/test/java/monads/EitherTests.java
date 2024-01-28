package monads;

import com.guerra08.monads.Either;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EitherTests {

    @Test
    void left_shouldBuildLeftEither() {
        Either<String, String> example = Either.left("Error");

        assertTrue(example.isLeft());
    }

    @Test
    void right_shouldBuildRightEither() {
        Either<String, String> example = Either.right("Value");

        assertTrue(example.isRight());
    }

    @Test
    void match_shouldMatchIntoHelloWorldValue() {
        Either<String, String> example = Either.right("A value");

        String result = example.match(
                left -> "An error",
                right -> "Hello World"
        );

        assertEquals("Hello World", result);
    }

    @Test
    void match_shouldMatchIntoErrorValue() {
        Either<String, String> example = Either.left("This is an error. ");

        String result = example.match(
                left -> "An error",
                right -> "Hello World"
        );

        assertEquals("An error", result);
    }

    @Test
    void map_shouldThrowRuntimeExceptionWhenMapOnLeft() {
        Either<String, String> example = Either.left("Error");

        assertThrows(RuntimeException.class, () -> example.map(String::toUpperCase));
    }

    @Test
    void map_shouldReturnRightMappedValue() {
        Either<String, String> example = Either.right("value");

        String result = example.map(String::toUpperCase);

        assertEquals("VALUE", result);
    }

    @Test
    void orNull_shouldReturnNullOnLeftEither() {
        Either<String, String> error = Either.left("Ooops!");

        String errorResult = error.orNull();

        assertNull(errorResult);
    }

    @Test
    void orNull_shouldReturnRightValueOnRightEither() {
        Either<String, String> ok = Either.right("value");

        String okResult = ok.orNull();

        assertEquals("value", okResult);
    }

    @Test
    void orElse_shouldReturnMappedValueOnLeftEither() {
        Either<String, Integer> error = Either.left("An error");

        Integer orElseResult = error.orElse(() -> 77);

        assertEquals(77, orElseResult);
    }

    @Test
    void orElse_shouldReturnValueOnRightEither() {
        Either<String, Integer> error = Either.right(66);

        Integer orElseResult = error.orElse(() -> 77);

        assertEquals(66, orElseResult);
    }

    @Test
    void isLeft_shouldReturnTrueIfLeft() {
        Either<String, Integer> error = Either.left("Unable to compute");

        assertFalse(error.isRight());
        assertTrue(error.isLeft());
    }

    @Test
    void isRight_shouldReturnTrueIfRight() {
        Either<String, Integer> error = Either.right(10);

        assertTrue(error.isRight());
        assertFalse(error.isLeft());
    }
}
