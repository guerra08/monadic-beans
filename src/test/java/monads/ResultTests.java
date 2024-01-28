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
}
