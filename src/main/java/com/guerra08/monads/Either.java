package com.guerra08.monads;

import java.util.function.Function;

/**
 * Either monad, used to represent a result that can be either types.
 * Usually used to encapsulate possible results with errors, instead of throwing an Exception
 *
 * @param <L> Left type (usually, the error type)
 * @param <R> Right type (the type of the value)
 */
public sealed interface Either<L, R> permits Either.Left, Either.Right {
    /**
     * Executes the left or right function if the current Either is Left or Right.
     * Returns the value from the executed branch.
     *
     * @param left  Function to be applied if Either is Left
     * @param right Function to be applied if Either is Right
     * @return <T> Result
     */
    <T, LR extends T, RR extends T> T match(Function<L, LR> left, Function<R, RR> right);

    /**
     * Returns if Either is of Left type
     *
     * @return boolean
     */
    boolean isLeft();

    /**
     * Returns if Either is of Right type
     *
     * @return boolean
     */
    boolean isRight();

    /**
     * Executes the mapper function if Either is of Right type
     *
     * @param mapper Function
     * @return <T>
     */
    <T, RR extends T> T map(Function<R, RR> mapper);

    /**
     * Returns the value if Right, or null if Left
     *
     * @return @Nullable R
     */
    R orNull();

    record Left<L, R>(L error) implements Either<L, R> {

        @Override
        public <T, LR extends T, RR extends T> T match(Function<L, LR> left, Function<R, RR> right) {
            return left.apply(error);
        }

        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public <T, RR extends T> T map(Function<R, RR> mapper) {
            throw new RuntimeException("Unable to use map with Left type.");
        }

        @Override
        public R orNull() {
            return null;
        }

    }

    record Right<L, R>(R value) implements Either<L, R> {

        @Override
        public <T, LR extends T, RR extends T> T match(Function<L, LR> left, Function<R, RR> right) {
            return right.apply(value);
        }

        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public <T, RR extends T> T map(Function<R, RR> mapper) {
            return mapper.apply(value);
        }

        @Override
        public R orNull() {
            return value;
        }

    }

    /**
     * Creates a new instance of Left type
     *
     * @param error <L> Left error
     * @return Left
     */
    static <L, R> Left<L, R> left(L error) {
        return new Left<>(error);
    }

    /**
     * Creates a new instance of Right type
     *
     * @param value <R> Right value
     * @return Right
     */
    static <L, R> Right<L, R> right(R value) {
        return new Right<>(value);
    }
}