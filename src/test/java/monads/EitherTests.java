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
        Either<String, String> ok = Either.right("value");

        String errorResult = error.rightOrNull();
        String okResult = ok.leftOrNull();

        Assertions.assertNull(errorResult);
        Assertions.assertNull(okResult);

    }

    @Test
    void shouldReturnValue() {

        Either<String, String> ok = Either.right("A value");
        Either<String, String> error = Either.left("An error");

        String okResult = ok.rightOrNull();
        String errorResult = error.leftOrNull();

        Assertions.assertEquals("A value", okResult);

        Assertions.assertEquals("An error", errorResult);

    }

}
