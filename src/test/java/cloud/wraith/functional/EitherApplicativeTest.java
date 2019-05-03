package cloud.wraith.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cloud.wraith.functional.Either.Right;

import java.util.function.Function;

import org.junit.Test;

/**
 * Unit test for Either class.
 */
public class EitherApplicativeTest {
    private static final String HELLO = "hello";
    private static final String WORLD = "world";
    private static final String LEFT_VALUE = "I am left-handed";
    private static final String LEFT_VALUE_DEFAULT = "I am left-handed by default";
    private static final String LEFT_FUNCTION_VALUE = "I am left-handed applicative function";

    /**
     * Test pure().
     */
    @Test
    public void shouldReturnARightFromPure() {
        final Either<String, String> expected = Either.<String, String>right(HELLO);
        final Either<String, String> actual = Either.<String, String>pure(HELLO);

        assertTrue("Should return a Right from pure()", actual instanceof Right);
        assertTrue("Should equal HELLO", expected.equals(actual));
        assertTrue("Should be different objects", expected != actual);
    }

    /**
     * Test apply() for a Left function and a Left value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldApplyLeftFunctionToALeftValue() {
        final Either<String, String> mx = Either.<String, String>left(LEFT_VALUE);
        final Either<String, Function<String, String>> left_g = Either.left(LEFT_FUNCTION_VALUE);
        final Either<String, String> actual = left_g.apply(mx);

        final Either<String, String> expected = Either.left(LEFT_FUNCTION_VALUE);

        assertTrue("Should apply a Left function to a Left value", expected.equals(actual));
    }

    /**
     * Test apply() for a Left function and a Right value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldApplyLeftFunctionToARightValue() {
        final String x = WORLD;
        final Either<String, String> mx = Either.<String, String>right(x);
        final Either<String, Function<String, String>> left_g = Either.left(LEFT_FUNCTION_VALUE);
        final Either<String, String> actual = left_g.apply(mx);

        final Either<String, String> expected = Either.left(LEFT_FUNCTION_VALUE);

        assertTrue("Should apply a Left function to a Right value", expected.equals(actual));
    }

    /**
     * Test apply() for a Right function and a Left value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldApplyRightFunctionToALeftValue() {
        final Function<String, String> g = a -> String.format("%s, %s", HELLO, a);
        final Either<String, String> mx = Either.<String, String>left(LEFT_VALUE);
        final Either<String, Function<String, String>> right_g = Either.pure(g);
        final Either<String, String> actual = right_g.apply(mx);

        final Either<String, String> expected = Either.left(LEFT_VALUE);

        assertTrue("Should apply a Right function to a Left value", expected.equals(actual));
    }

    /**
     * Test apply() for a Right function and a Right value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldApplyRightFunctionToARightValue() {
        final Function<String, String> g = a -> String.format("%s, %s", HELLO, a);
        final String x = WORLD;
        final Either<String, String> mx = Either.<String, String>right(x);
        final Either<String, Function<String, String>> right_g = Either.pure(g);
        final Either<String, String> actual = right_g.apply(mx);

        final Either<String, String> expected = Either.right(String.format("%s, %s", HELLO, WORLD));

        assertTrue("Should apply a Right", expected.equals(actual));
    }

    /**
     * Test that Left satisfies the Applicative laws.
     *   See Hutton; Programming in Haskell; p163
     *
     * <p>For the identity function, id, and a mapping function g:
     * {@code}
     * pure id <*> x = x
     * pure (g x) = pure g <*> pure x
     * x <*> pure y = pure (\g -> g y) <*> x
     * x <*> (y <*> z) = (pure (.) <*> x <*> y) <*> z
     * {code}
     */
    @Test
    public void shouldSatisfyApplicativeLawsForALeft() {
        final int I = 7;
        final Integer i = Integer.valueOf(I);

        // Identity function
        final Function<Integer, Integer> id = x -> x;

        // Two mapping functions
        final Function<Integer, Integer> f = x -> 3 + x;
        final Function<Integer, Integer> g = x -> 256 * x;

        // Law #1
        // x is a Left
        // Expected
        final Either<String, Integer> expected1 = Either.<String, Integer>left(LEFT_VALUE);

        // Actual
        final Either<String, Integer> actual1 = Either.<String, Function<Integer, Integer>>pure(id).apply(Either.<String, Integer>left(LEFT_VALUE));

        assertEquals("Should satify Applicative law #1 with apply", expected1, actual1);
        assertTrue("Should satify Applicative law #1 with apply", expected1.equals(actual1));

        // Law #2
        // There's no left side of a pure, so same as for a Right.
        // Expected
        final Either<String, Integer> expected2 = Either.<String, Integer>pure(g.apply(i));

        // Actual
        final Either<String, Integer> actual2 = Either.<String, Function<Integer, Integer>>pure(g).apply(Either.<String, Integer>pure(i));

        assertEquals("Should satify Applicative law #2 with apply", expected2, actual2);
        assertTrue("Should satify Applicative law #2 with apply", expected2.equals(actual2));

        // Law #3
        // x is a Left
        // Lambda function:
        // lambda :: g -> g y
        final Function<Function<Integer, Integer>, Integer> lambda = fxn -> fxn.apply(i);

        // Expected
        final Either<String, Integer> expected3 = Either.<String, Function<Integer, Integer>>left(LEFT_VALUE).apply(Either.<String, Integer>pure(i));

        // Actual
        final Either<String, Integer> actual3 = Either.<String, Function<Function<Integer, Integer>, Integer>>pure(lambda)
            .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE));

        assertEquals("Should satify Applicative law #3 with apply", expected3, actual3);
        assertTrue("Should satify Applicative law #3 with apply", expected3.equals(actual3));

        // Law #4
        // Compose function:
        // (.) :: (b -> c) -> (a -> b) -> (a -> c)
        final Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose = f1 -> g1 -> (x -> f1.apply(g1.apply(x)));

        // First function goes left.
        // Expected
        final Either<String, Integer> expected41 = Either.<String, Function<Integer, Integer>>left(LEFT_VALUE)
                .apply(Either.<String, Function<Integer, Integer>>right(g)
                    .apply(Either.<String, Integer>right(i))
                );

        // Actual
        final Either<String, Integer> actual41 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE))
                .apply(Either.<String, Function<Integer, Integer>>right(g))
        ).apply(Either.<String, Integer>right(i));

        assertTrue("Should be a left", actual41.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual41.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with apply", expected41, actual41);
        assertTrue("Should satify Applicative law #4 with apply", expected41.equals(actual41));

        // Second function goes left.
        // Expected
        final Either<String, Integer> expected42 = Either.<String, Function<Integer, Integer>>right(f)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE)
                    .apply(Either.<String, Integer>right(i))
                );

        // Actual
        final Either<String, Integer> actual42 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>right(f))
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE))
        ).apply(Either.<String, Integer>right(i));

        assertTrue("Should be a left", actual42.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual42.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with apply", expected42, actual42);
        assertTrue("Should satify Applicative law #4 with apply", expected42.equals(actual42));

        // Both functions go left.
        // Expected
        final Either<String, Integer> expected43 = Either.<String, Function<Integer, Integer>>left(LEFT_VALUE_DEFAULT)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE)
                    .apply(Either.<String, Integer>right(i))
                );

        // Actual
        final Either<String, Integer> actual43 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE_DEFAULT))
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE))
        ).apply(Either.<String, Integer>right(i));

        assertTrue("Should be a left", actual43.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual43.equals(Either.<String, Integer>left(LEFT_VALUE_DEFAULT)));
        assertEquals("Should satify Applicative law #4 with apply", expected43, actual43);
        assertTrue("Should satify Applicative law #4 with apply", expected43.equals(actual43));

        // The value goes left.
        // Expected
        final Either<String, Integer> expected44 = Either.<String, Function<Integer, Integer>>right(f)
                .apply(Either.<String, Function<Integer, Integer>>right(g)
                    .apply(Either.<String, Integer>left(LEFT_VALUE))
                );

        // Actual
        final Either<String, Integer> actual44 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>right(f))
                .apply(Either.<String, Function<Integer, Integer>>right(g))
        ).apply(Either.<String, Integer>left(LEFT_VALUE));

        assertTrue("Should be a left", actual44.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual44.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with apply", expected44, actual44);
        assertTrue("Should satify Applicative law #4 with apply", expected44.equals(actual44));

        // All goes left.
        // Expected
        final Either<String, Integer> expected45 = Either.<String, Function<Integer, Integer>>left(LEFT_VALUE_DEFAULT)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE)
                    .apply(Either.<String, Integer>left(LEFT_VALUE))
                );

        // Actual
        final Either<String, Integer> actual45 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE_DEFAULT))
                .apply(Either.<String, Function<Integer, Integer>>left(LEFT_VALUE))
        ).apply(Either.<String, Integer>left(LEFT_VALUE));

        assertTrue("Should be a left", actual45.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual45.equals(Either.<String, Integer>left(LEFT_VALUE_DEFAULT)));
        assertEquals("Should satify Applicative law #4 with apply", expected45, actual45);
        assertTrue("Should satify Applicative law #4 with apply", expected45.equals(actual45));

    }

    /**
     * Test that Right satisfies the Applicative laws.
     *   See Hutton; Programming in Haskell; p163
     *
     * <p>For the identity function, id, and a mapping function g:
     * {@code}
     * pure id <*> x = x
     * pure (g x) = pure g <*> pure x
     * x <*> pure y = pure (\g -> g y) <*> x
     * x <*> (y <*> z) = (pure (.) <*> x <*> y) <*> z
     * {code}
     */
    @Test
    public void shouldSatisfyApplicativeLawsForARight() {
        final int I = 7;
        final Integer i = Integer.valueOf(I);

        // Identity function
        final Function<Integer, Integer> id = x -> x;

        // Two mapping functions
        final Function<Integer, Integer> f = x -> 3 + x;
        final Function<Integer, Integer> g = x -> 256 * x;

        // Law #1
        // Expected
        final Either<String, Integer> expected1 = Either.<String, Integer>right(i);

        // Actual
        final Either<String, Integer> actual1 = Either.<String, Function<Integer, Integer>>pure(id).apply(Either.<String, Integer>right(i));

        assertEquals("Should satify Applicative law #1 with apply", expected1, actual1);
        assertTrue("Should satify Applicative law #1 with apply", expected1.equals(actual1));

        // Law #2
        // Expected
        final Either<String, Integer> expected2 = Either.<String, Integer>pure(g.apply(i));

        // Actual
        final Either<String, Integer> actual2 = Either.<String, Function<Integer, Integer>>pure(g).apply(Either.<String, Integer>pure(i));

        assertEquals("Should satify Applicative law #2 with apply", expected2, actual2);
        assertTrue("Should satify Applicative law #2 with apply", expected2.equals(actual2));

        // Law #3
        // Lambda function:
        // lambda :: g -> g y
        final Function<Function<Integer, Integer>, Integer> lambda = fxn -> fxn.apply(i);

        // Expected
        final Either<String, Integer> expected3 = Either.<String, Function<Integer, Integer>>right(g).apply(Either.<String, Integer>pure(i));

        // Actual
        final Either<String, Integer> actual3 = Either.<String, Function<Function<Integer, Integer>, Integer>>pure(lambda)
            .apply(Either.<String, Function<Integer, Integer>>right(g));

        assertEquals("Should satify Applicative law #3 with apply", expected3, actual3);
        assertTrue("Should satify Applicative law #3 with apply", expected3.equals(actual3));

        // Law #4
        // Compose function:
        // (.) :: (b -> c) -> (a -> b) -> (a -> c)
        final Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose = f1 -> g1 -> (x -> f1.apply(g1.apply(x)));

        // Expected
        final Either<String, Integer> expected4 = Either.<String, Function<Integer, Integer>>right(f)
                .apply(Either.<String, Function<Integer, Integer>>right(g)
                    .apply(Either.<String, Integer>right(i))
                );

        // Actual
        final Either<String, Integer> actual4 = (
            Either.<String, Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>>>pure(compose)
                .apply(Either.<String, Function<Integer, Integer>>right(f))
                .apply(Either.<String, Function<Integer, Integer>>right(g))
        ).apply(Either.<String, Integer>right(i));

        assertEquals("Should satify Applicative law #4 with apply", expected4, actual4);
        assertTrue("Should satify Applicative law #4 with apply", expected4.equals(actual4));
    }

    /**
     * Test fapply() for a Left function and a Left value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldFApplyLeftFunctionToALeftValue() {

        final Either<String, String> actual = Either.fapply(
            Either.left(LEFT_FUNCTION_VALUE),
            Either.left(LEFT_VALUE)
        );

        final Either<String, String> expected = Either.left(LEFT_FUNCTION_VALUE);

        assertTrue("Should fapply a Left function to a Left value", expected.equals(actual));
    }

    /**
     * Test fapply() for a Left function and a Right value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldFApplyLeftFunctionToARightValue() {

        final Either<String, String> actual = Either.fapply(
            Either.left(LEFT_FUNCTION_VALUE),
            Either.right(WORLD)
        );

        final Either<String, String> expected = Either.left(LEFT_FUNCTION_VALUE);

        assertTrue("Should fapply a Left function to a Right value", expected.equals(actual));
    }

    /**
     * Test fapply() for a Right function and a Left value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldFApplyRightFunctionToALeftValue() {
        final Function<String, String> g = a -> String.format("%s, %s", HELLO, a);

        final Either<String, String> actual = Either.fapply(
            Either.pure(g),
            Either.left(LEFT_VALUE)
        );

        final Either<String, String> expected = Either.left(LEFT_VALUE);

        assertTrue("Should fapply a Right function to a Left value", expected.equals(actual));
    }

    /**
     * Test fapply() for a Right function and a Right value.
     * {@code}
     * class Functor f => Applicative f where
     *    pure :: a -> f a
     *    (<*>) :: f (a -> b) -> f a -> f b
     *
     * <p>instance Applicative Either where
     *    pure = Right
     *    Left <*> _ = Left
     *    (Right g) <*> mx = fmap g mx
     * {code}
     */
    @Test
    public void shouldFApplyRightFunctionToARightValue() {
        final Function<String, String> g = a -> String.format("%s, %s", HELLO, a);

        final Either<String, String> actual = Either.fapply(
            Either.pure(g),
            Either.right(WORLD)
        );

        final Either<String, String> expected = Either.right(String.format("%s, %s", HELLO, WORLD));

        assertTrue("Should fapply a Right", expected.equals(actual));
    }

    /**
     * Test that Left satisfies the Applicative laws.
     *   See Hutton; Programming in Haskell; p163
     *
     * <p>For the identity function, id, and a mapping function g:
     * {@code}
     * pure id <*> x = x
     * pure (g x) = pure g <*> pure x
     * x <*> pure y = pure (\g -> g y) <*> x
     * x <*> (y <*> z) = (pure (.) <*> x <*> y) <*> z
     * {code}
     */
    @Test
    public void shouldSatisfyFApplicativeLawsForALeft() {
        final int I = 7;
        final Integer i = Integer.valueOf(I);

        // Identity function
        final Function<Integer, Integer> id = x -> x;

        // Two mapping functions
        final Function<Integer, Integer> f = x -> 3 + x;
        final Function<Integer, Integer> g = x -> 256 * x;

        // Law #1
        // x is a Left
        // Expected
        final Either<String, Integer> expected1 = Either.<String, Integer>left(LEFT_VALUE);

        // Actual
        final Either<String, Integer> actual1 = Either.fapply(
            Either.pure(id),
            Either.left(LEFT_VALUE)
        );

        assertEquals("Should satify Applicative law #1 with fapply", expected1, actual1);
        assertTrue("Should satify Applicative law #1 with fapply", expected1.equals(actual1));

        // Law #2
        // There's no left side of a pure, so same as for a Right.
        // Expected
        final Either<String, Integer> expected2 = Either.<String, Integer>pure(g.apply(i));

        // Actual
        final Either<String, Integer> actual2 = Either.fapply(
            Either.pure(g),
            Either.pure(i)
        );

        assertEquals("Should satify Applicative law #2 with fapply", expected2, actual2);
        assertTrue("Should satify Applicative law #2 with fapply", expected2.equals(actual2));

        // Law #3
        // x is a Left
        // Lambda function:
        // lambda :: g -> g y
        final Function<Function<Integer, Integer>, Integer> lambda = fxn -> fxn.apply(i);

        // Expected
        final Either<String, Integer> expected3 = Either.fapply(
            Either.left(LEFT_VALUE),
            Either.pure(i)
        );

        // Actual
        final Either<String, Integer> actual3 = Either.fapply(
            Either.pure(lambda),
            Either.left(LEFT_VALUE)
        );

        assertEquals("Should satify Applicative law #3 with fapply", expected3, actual3);
        assertTrue("Should satify Applicative law #3 with fapply", expected3.equals(actual3));

        // Law #4
        // Compose function:
        // (.) :: (b -> c) -> (a -> b) -> (a -> c)
        final Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose = f1 -> g1 -> (x -> f1.apply(g1.apply(x)));

        // First function goes left.
        // Expected
        final Either<String, Integer> expected41 = Either.fapply(
            Either.left(LEFT_VALUE),
            Either.fapply(
                Either.right(g),
                Either.right(i)
            )
        );

        // Actual
        final Either<String, Integer> actual41 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.left(LEFT_VALUE)
                ),
                Either.right(g)
            ),
            Either.right(i)
        );

        assertTrue("Should be a left", actual41.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual41.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with fapply", expected41, actual41);
        assertTrue("Should satify Applicative law #4 with fapply", expected41.equals(actual41));

        // Second function goes left.
        // Expected
        final Either<String, Integer> expected42 = Either.fapply(
            Either.right(f),
            Either.fapply(
                Either.left(LEFT_VALUE),
                Either.right(i)
            )
        );

        // Actual
        final Either<String, Integer> actual42 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.right(f)
                ),
                Either.left(LEFT_VALUE)
            ),
            Either.right(i)
        );

        assertTrue("Should be a left", actual42.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual42.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with fapply", expected42, actual42);
        assertTrue("Should satify Applicative law #4 with fapply", expected42.equals(actual42));

        // Both functions go left.
        // Expected
        final Either<String, Integer> expected43 = Either.fapply(
            Either.left(LEFT_VALUE_DEFAULT),
            Either.fapply(
                Either.left(LEFT_VALUE),
                Either.right(i)
            )
        );

        // Actual
        final Either<String, Integer> actual43 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.left(LEFT_VALUE_DEFAULT)
                ),
                Either.left(LEFT_VALUE)
            ),
            Either.right(i)
        );

        assertTrue("Should be a left", actual43.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual43.equals(Either.<String, Integer>left(LEFT_VALUE_DEFAULT)));
        assertEquals("Should satify Applicative law #4 with fapply", expected43, actual43);
        assertTrue("Should satify Applicative law #4 with fapply", expected43.equals(actual43));

        // The value goes left.
        // Expected
        final Either<String, Integer> expected44 = Either.fapply(
            Either.right(f),
            Either.fapply(
                Either.right(g),
                Either.left(LEFT_VALUE)
            )
        );

        // Actual
        final Either<String, Integer> actual44 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.right(f)
                ),
                Either.right(g)
            ),
            Either.left(LEFT_VALUE)
        );

        assertTrue("Should be a left", actual44.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual44.equals(Either.<String, Integer>left(LEFT_VALUE)));
        assertEquals("Should satify Applicative law #4 with fapply", expected44, actual44);
        assertTrue("Should satify Applicative law #4 with fapply", expected44.equals(actual44));

        // All goes left.
        // Expected
        final Either<String, Integer> expected45 = Either.fapply(
            Either.left(LEFT_VALUE_DEFAULT),
            Either.fapply(
                Either.left(LEFT_VALUE),
                Either.left(LEFT_VALUE)
            )
        );

        // Actual
        final Either<String, Integer> actual45 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.left(LEFT_VALUE_DEFAULT)
                ),
                Either.left(LEFT_VALUE)
            ),
            Either.left(LEFT_VALUE)
        );

        assertTrue("Should be a left", actual45.isLeft());
        assertTrue("Should be a LEFT_VALUE", actual45.equals(Either.<String, Integer>left(LEFT_VALUE_DEFAULT)));
        assertEquals("Should satify Applicative law #4 with fapply", expected45, actual45);
        assertTrue("Should satify Applicative law #4 with fapply", expected45.equals(actual45));

    }

    /**
     * Test that Right satisfies the Applicative laws.
     *   See Hutton; Programming in Haskell; p163
     *
     * <p>For the identity function, id, and a mapping function g:
     * {@code}
     * pure id <*> x = x
     * pure (g x) = pure g <*> pure x
     * x <*> pure y = pure (\g -> g y) <*> x
     * x <*> (y <*> z) = (pure (.) <*> x <*> y) <*> z
     * {code}
     */
    @Test
    public void shouldSatisfyFApplicativeLawsForARight() {
        final int I = 7;
        final Integer i = Integer.valueOf(I);

        // Identity function
        final Function<Integer, Integer> id = x -> x;

        // Two mapping functions
        final Function<Integer, Integer> f = x -> 3 + x;
        final Function<Integer, Integer> g = x -> 256 * x;

        // Law #1
        // Expected
        final Either<String, Integer> expected1 = Either.right(i);

        // Actual
        final Either<String, Integer> actual1 = Either.fapply(Either.pure(id), Either.right(i));

        assertEquals("Should satify Applicative law #1 with fapply", expected1, actual1);
        assertTrue("Should satify Applicative law #1 with fapply", expected1.equals(actual1));

        // Law #2
        // Expected
        final Either<String, Integer> expected2 = Either.pure(g.apply(i));

        // Actual
        final Either<String, Integer> actual2 = Either.fapply(Either.pure(g), Either.pure(i));

        assertEquals("Should satify Applicative law #2 with fapply", expected2, actual2);
        assertTrue("Should satify Applicative law #2 with fapply", expected2.equals(actual2));

        // Law #3
        // Lambda function:
        // lambda :: g -> g y
        final Function<Function<Integer, Integer>, Integer> lambda = fxn -> fxn.apply(i);

        // Expected
        final Either<String, Integer> expected3 = Either.fapply(Either.right(g), Either.pure(i));

        // Actual
        final Either<String, Integer> actual3 = Either.fapply(Either.pure(lambda), Either.right(g));

        assertEquals("Should satify Applicative law #3 with fapply", expected3, actual3);
        assertTrue("Should satify Applicative law #3 with fapply", expected3.equals(actual3));

        // Law #4
        // Compose function:
        // (.) :: (b -> c) -> (a -> b) -> (a -> c)
        final Function<Function<Integer, Integer>, Function<Function<Integer, Integer>, Function<Integer, Integer>>> compose = f1 -> g1 -> (x -> f1.apply(g1.apply(x)));

        // Expected
        final Either<String, Integer> expected4 = Either.fapply(
            Either.right(f),
            Either.fapply(
                Either.right(g),
                Either.right(i)
            )
        );

        // Actual
        final Either<String, Integer> actual4 = Either.fapply(
            Either.fapply(
                Either.fapply(
                    Either.pure(compose),
                    Either.right(f)
                ),
                Either.right(g)
            ),
            Either.right(i)
        );

        assertEquals("Should satify Applicative law #4 with fapply", expected4, actual4);
        assertTrue("Should satify Applicative law #4 with fapply", expected4.equals(actual4));
    }
}
