package cloud.wraith.functional;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.Objects;
import java.util.Optional;

public abstract class Either<L, A> {

    /**
     * Hide the noargs constructor.
     */
    private Either() {
    }

    public static <L, A> Left<L, A> left(final L value) {
        return Left.of(value);
    }

    public static <L, A> Right<L, A> right(final A value) {
        return Right.of(value);
    }

    public abstract boolean isLeft();

    public abstract boolean isRight();

    public abstract Optional<A> get();

    public abstract A getOrElse(final A defaultValue);

    public abstract A getOrElse(Supplier<? extends A> supplier);

    public abstract Optional<L> getLeft();

    public abstract L getLeftOrElse(final L defaultValue);

    public abstract L getLeftOrElse(Supplier<? extends L> supplier);

    public abstract Either<L, A> peek(Consumer<? super A> consumer);

    public abstract Either<L, A> peekLeft(Consumer<? super L> consumer);

    public abstract Either<L, A> biPeek(Consumer<? super L> consumerL, Consumer<? super A> consumerR);

    static final class Left<L, A> extends Either<L, A> {
        private final L value;

        private Left(final L value) {
            this.value = Objects.requireNonNull(value);
        }

        private static <L, A> Left<L, A> of(final L value) {
            return new Left<L, A>(value);
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
        public Optional<A> get() {
            return Optional.empty();
        }

        @Override
        public A getOrElse(final A defaultValue) {
            return Objects.requireNonNull(defaultValue);
        }

        @Override
        public A getOrElse(Supplier<? extends A> supplier) {
            return Objects.requireNonNull(supplier).get();
        }

        @Override
        public Optional<L> getLeft() {
            return Optional.of(value);
        }

        @Override
        public L getLeftOrElse(final L defaultValue) {
            return value;
        }

        @Override
        public L getLeftOrElse(Supplier<? extends L> supplier) {
            return value;
        }

        @Override
        public Either<L, A> peek(Consumer<? super A> consumer) {
            return this;
        }

        @Override
        public Either<L, A> peekLeft(Consumer<? super L> consumer) {
            Objects.requireNonNull(consumer).accept(value);
            return this;
        }

        @Override
        public Either<L, A> biPeek(Consumer<? super L> consumerL, Consumer<? super A> consumerR) {
            Objects.requireNonNull(consumerL).accept(value);
            return this;
        }

        @Override
        public String toString() {
            return String.format("Either.left[%s]", value.toString());
        }

    }

    static final class Right<L, A> extends Either<L, A> {
        private final A value;

        private Right(final A value) {
            this.value = Objects.requireNonNull(value);
        }

        private static <L, A> Right<L, A> of(final A value) {
            return new Right<L, A>(value);
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
        public Optional<A> get() {
            return Optional.of(value);
        }

        @Override
        public A getOrElse(final A defaultValue) {
            return value;
        }

        @Override
        public A getOrElse(Supplier<? extends A> supplier) {
            return value;
        }

        @Override
        public Optional<L> getLeft() {
            return Optional.empty();
        }

        @Override
        public L getLeftOrElse(final L defaultValue) {
            return Objects.requireNonNull(defaultValue);
        }

        @Override
        public L getLeftOrElse(Supplier<? extends L> supplier) {
            return Objects.requireNonNull(supplier).get();
        }

        @Override
        public Either<L, A> peek(Consumer<? super A> consumer) {
            Objects.requireNonNull(consumer).accept(value);
            return this;
        }

        @Override
        public Either<L, A> peekLeft(Consumer<? super L> consumer) {
            return this;
        }

        @Override
        public Either<L, A> biPeek(Consumer<? super L> consumerL, Consumer<? super A> consumerR) {
            Objects.requireNonNull(consumerR).accept(value);
            return this;
        }

        @Override
        public String toString() {
            return String.format("Either.right[%s]", value.toString());
        }

    }

}
