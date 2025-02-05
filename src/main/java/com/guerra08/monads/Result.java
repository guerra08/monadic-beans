package com.guerra08.monads;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Result monad, which encapsulates a value of type T, or a Throwable if result of computation is an error.
 */
public sealed interface Result<T> permits Result.Ok, Result.Error {

    /**
     * Returns if Result is of Ok type, meaning that the computation executed successfully
     * @return boolean
     */
    boolean isOk();

    /**
     * Returns if Result is of Error type, meaning that the computation executed with an error
     * @return boolean
     */
    boolean isError();

    /**
     * Executes the ok or error function if the current Either is Ok or Error.
     * Returns the value from the executed branch.
     * @param ok  Function to be applied if Either is Left
     * @param error Function to be applied if Either is Right
     * @return <T> Result
     */
    <Y, O extends Y, E extends Y> Y match(Function<T, O> ok, Function<Throwable, E> error);

    record Ok<T>(T value) implements Result<T> {

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public <Y, O extends Y, E extends Y> Y match(Function<T, O> ok, Function<Throwable, E> error) {
            return ok.apply(value);
        }

    }

    record Error<T>(Throwable t) implements Result<T> {

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public <Y, O extends Y, E extends Y> Y match(Function<T, O> ok, Function<Throwable, E> error) {
            return error.apply(t);
        }

    }

    /**
     * Applies a mapping function if Result is of type Ok
     * @param mapper The mapper function
     * @return Result of the mapping operation
     */
    default <U> Result<U> map(Function<T, U> mapper) {
        if (this.isOk()) {
            // If mapper throws, we return an error
            try {
                return Result.ok(mapper.apply(((Ok<T>) this).value()));
            } catch (Exception e) {
                return Result.error(e);
            }
        }
        return Result.error(((Error<T>) this).t());
    }

    /**
     * Recovers by using a function from throwable to value T
     * @param recoveryFunction Function for recovery scenario
     * @return Result of recovery
     */
    default T recover(Function<Throwable, T> recoveryFunction) {
        return isOk() ? ((Ok<T>) this).value() : recoveryFunction.apply(((Error<T>) this).t());
    }

    /**
     * Creates a new instance of Ok type
     * @param value <T> Ok value
     * @return Ok instance
     */
    static <T> Ok<T> ok(T value) {
        return new Ok<>(value);
    }

    /**
     * Creates a new instance of Ok type
     * @param t Throwable
     * @return Error instance
     */
    static <T> Error<T> error(Throwable t) {
        return new Error<>(t);
    }

    /**
     * Creates a Result from a given Supplier
     * @param supplier Supplier of a given type
     * @return Result of Ok or Error from Supplier
     * @param <T> Type
     */
    static <T> Result<T> of(Supplier<T> supplier) {
        try {
            return new Ok<>(supplier.get());
        } catch (Throwable t) {
            return new Error<>(t);
        }
    }

}

