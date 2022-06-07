package monads;

import com.guerra08.monads.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class EitherTests {

    @Test
    void shouldBeLeft() {
        Either<String, String> example = Either.left("Error");

        Assertions.assertTrue(example.isLeft());
    }

    @Test
    void shouldBeRight() {
        Either<String, String> example = Either.right("Value");

        Assertions.assertTrue(example.isRight());
    }

    @Test
    void shouldMatchIntoHelloWorldString() {

        Either<String, String> example = Either.right("A value");

        String result = example.match(
                left -> "An error",
                right -> "Hello World"
        );

        Assertions.assertEquals("Hello World", result);

    }

    @Test
    void shouldThrowRuntimeExceptionWhenMapOnLeft() {

        Either<String, String> example = Either.left("Error");

        Assertions.assertThrows(RuntimeException.class, () -> example.map(String::toUpperCase));

    }

    @Test
    void shouldReturnMappedValue() {

        Either<String, String> example = Either.right("value");

        String result = example.map(String::toUpperCase);

        Assertions.assertEquals("VALUE", result);

    }

    @Test
    void shouldReturnNull() {

        Either<String, String> error = Either.left("Ooops!");

        String result = error.orNull();

        Assertions.assertNull(result);

    }

    @Test
    void shouldReturnValue() {

        Either<String, String> error = Either.right("A value");

        String result = error.orNull();

        Assertions.assertEquals("A value", result);

    }

}
