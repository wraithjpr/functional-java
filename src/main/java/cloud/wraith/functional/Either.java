package cloud.wraith.functional;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Either monad.
 *
 * <p>{@code} data Either l a = Left l a | Right l a
 *
 * <p>instance Functor Either where -- fmap :: (a -> b) -> Either l a -> Either l b
 * fmap _ (Left x _) = Left x _ fmap g (Right _ x) = Right _ (g x) {code}
 */
public abstract class Either<L, A> {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 0L;

    /**
     * Hide the noargs constructor.
     */
    private Either() {
    }

    public static <L, A> Left<L, A> left(final L value) {
        Objects.requireNonNull(value);
        return Left.of(value);
    }

    public static <L, A> Right<L, A> right(final A value) {
        Objects.requireNonNull(value);
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

    /**
     * Functor fmap function.
     *
     * <p>{@code} fmap :: (a -> b) -> Either l a -> Either l b {code}
     *
     * @param <A> The right-hand type of the argument Either
     * @param <B> The right-hand type of the result Either
     * @param <L> The left-hand type of both argument and result Eithers
     * @param fxn The mapping function; maps argument values in the type A to result values in the type B
     * @param fa  The source Either&lt;L,&nbsp;A&gt; functor
     * @return The result Either&lt;L,&nbsp;B&gt; functor
     */
    public static <L, B, A> Either<L, B> fmap(Function<? super A, ? extends B> fxn, final Either<L, A> fa) {
        Objects.requireNonNull(fxn);
        Objects.requireNonNull(fa);

        return fa.map(fxn);
    }

    /**
     * Map function.
     *
     * @param <B> The right-hand type of the result Either
     * @param fxn The mapping function; maps argument values in the type A to result values in the type B
     * @return The result Either&lt;L,&nbsp;B&gt;
     */
    public abstract <B> Either<L, B> map(Function<? super A, ? extends B> fxn);

    /**
     * Applicative pure function.
     * Converts a value of type A into a structure of type Either&lt;L,&nbsp;A&gt;.
     * The result is right-handed.
     *
     * <p>{@code} pure :: a -> Right l a {code}
     *
     * @param <A>   The right-hand type of the result Either and the argument type
     * @param <L>   The left-hand type of the result Either
     * @param value The value of type A
     * @return A structure of type Either&lt;L,&nbsp;A&gt;
     */
    public static <L, A> Either<L, A> pure(final A value) {
        Objects.requireNonNull(value);

        return right(value);
    }

    /**
     * Applicative application function.
     *
     * @param <A> The right-hand type of the argument Either
     * @param <B> The right-hand type of the result Either
     * @param <L> The left-hand type of both argument and result Eithers
     * @param ffxn The mapping function; maps argument values in the type A to result values in the type B
     * @param fa  The source Either&lt;L,&nbsp;A&gt; functor
     * @return The result Either&lt;L,&nbsp;B&gt; functor
     */
    public static <A, B, L> Either<L, B> fapply(final Either<L, Function<? super A, ? extends B>> ffxn, final Either<L, A> fa) {
        Objects.requireNonNull(ffxn);
        Objects.requireNonNull(fa);

        return ffxn.isLeft() ? Either.<L, B>left(ffxn.getLeft().get()) : fmap(ffxn.get().get(), fa);
    }

    /**
     * Applicative application function.
     *
     * @param <A1> The right-hand type of the argument Either
     * @param <B> The right-hand type of the result Either
     * @param fa The functor to map using the mapping function wrapped by this Either
     * @return The result Either&lt;L,&nbsp;B&gt;
     */
    public abstract <B, A1> Either<L, B> apply(Either<L, A1> fa);

    /**
     * Left implementation of Either.
     */
    static final class Left<L, A> extends Either<L, A> {
        @SuppressWarnings("unused")
        private static final long serialVersionUID = 0L;

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
        public <B> Either<L, B> map(Function<? super A, ? extends B> fxn) {
            return Either.<L, B>left(value);
        }

        @Override
        public <B, A1> Either<L, B> apply(Either<L, A1> fa) {
            return Either.<L, B>left(value);
        }

        @Override
        public String toString() {
            return String.format("Either.left[%s]", value.toString());
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Either)) {
                return false;
            }

            Either<?, ?> other = (Either<?, ?>) obj;

            if (!other.isLeft()) {
                return false;
            }

            return Objects.equals(value, other.getLeft().get());
        }

    }

    /**
     * Right implementation of Either.
     */
    static final class Right<L, A> extends Either<L, A> {
        @SuppressWarnings("unused")
        private static final long serialVersionUID = 0L;

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
        public <B> Either<L, B> map(Function<? super A, ? extends B> fxn) {
            Objects.requireNonNull(fxn);

            return Either.<L, B>right(fxn.apply(value));
        }

        @Override
        public <B, A1> Either<L, B> apply(Either<L, A1> fa) {
            Objects.requireNonNull(fa);
            Objects.requireNonNull(value instanceof Function<?, ?> ? value : null);

            @SuppressWarnings("unchecked")
            Function<? super A1, ? extends B> fxn = (Function<? super A1, ? extends B>) value;

            return fa.map(fxn);
        }

        @Override
        public String toString() {
            return String.format("Either.right[%s]", value.toString());
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Either)) {
                return false;
            }

            Either<?, ?> other = (Either<?, ?>) obj;

            if (!other.isRight()) {
                return false;
            }

            return Objects.equals(value, other.get().get());
        }

    }

}
