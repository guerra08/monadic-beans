package monads;

import com.guerra08.monads.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EitherTests {

    @Test
    void left_shouldBuildLeftEither() {
        Either<String, String> example = Either.left("Error");

        Assertions.assertTrue(example.isLeft());
    }

    @Test
    void right_shouldBuildRightEither() {
        Either<String, String> example = Either.right("Value");

        Assertions.assertTrue(example.isRight());
    }

    @Test
    void match_shouldMatchIntoHelloWorldValue() {

        Either<String, String> example = Either.right("A value");

        String result = example.match(
                left -> "An error",
                right -> "Hello World"
        );

        Assertions.assertEquals("Hello World", result);

    }

    @Test
    void map_shouldThrowRuntimeExceptionWhenMapOnLeft() {

        Either<String, String> example = Either.left("Error");

        Assertions.assertThrows(RuntimeException.class, () -> example.map(String::toUpperCase));

    }

    @Test
    void map_shouldReturnRightMappedValue() {

        Either<String, String> example = Either.right("value");

        String result = example.map(String::toUpperCase);

        Assertions.assertEquals("VALUE", result);

    }

    @Test
    void orNull_shouldReturnNullOnLeftEither() {

        Either<String, String> error = Either.left("Ooops!");

        String errorResult = error.orNull();

        Assertions.assertNull(errorResult);

    }

    @Test
    void orNull_shouldReturnRightValueOnRightEither() {

        Either<String, String> ok = Either.right("value");

        String okResult = ok.orNull();

        Assertions.assertEquals("value", okResult);

    }

    @Test
    void orElse_shouldReturnMappedValueOnLeftEither() {
      
        Either<String, Integer> error = Either.left("An error");

        Integer orElseResult = error.orElse(() -> 77);

        Assertions.assertEquals(77, orElseResult);

    }

    @Test
    void orElse_shouldReturnValueOnRightEither() {

        Either<String, Integer> error = Either.right(66);

        Integer orElseResult = error.orElse(() -> 77);

        Assertions.assertEquals(66, orElseResult);

    }

}
