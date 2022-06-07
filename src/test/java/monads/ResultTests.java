package monads;

import com.guerra08.monads.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTests {

    @Test
    void shouldBeErrorResult() {

        Result<String> result = Result.error(new IllegalArgumentException("An error happened."));

        Assertions.assertTrue(result.isError());

    }

    @Test
    void shouldBeOkResult() {

        Result<String> result = Result.ok("Ok!");

        Assertions.assertTrue(result.isOk());

    }

    @Test
    void shouldMatchToString() {

        Result<String> result = Result.ok("Ok!");

        String value = result.match(
                ok -> ok,
                Throwable::getMessage
        );

        Assertions.assertEquals("Ok!", value);

    }

}
