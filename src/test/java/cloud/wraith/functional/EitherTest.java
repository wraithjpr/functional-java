package cloud.wraith.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import cloud.wraith.functional.Either.Left;
import cloud.wraith.functional.Either.Right;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

/**
 * Unit test for Either class.
 */
public class EitherTest {
    private static final String GOODBYE = "goodbye";
    private static final String HELLO = "hello";
    private static final String WORLD = "world";
    private static final String LEFT_VALUE = "I am left-handed";
    private static final String LEFT_VALUE_DEFAULT = "I am left-handed by default";
    private static final String LEFT_FUNCTION_VALUE = "I am left-handed applicative function";

    /**
     * Test a Left constructor.
     */
    @Test
    public void shouldConstructALeft() {
        assertTrue("Should return a Left", Either.left(LEFT_VALUE) instanceof Left);
    }

    /**
     * Test a Right constructor.
     */
    @Test
    public void shouldConstructARight() {
        assertTrue("Should return a Right", Either.right(HELLO) instanceof Right);
    }

    /**
     * Test a isLeft() and isRight() for a Left.
     */
    @Test
    public void shouldBeALeft() {
        final Either<String, String> left = Either.left(LEFT_VALUE);

        assertTrue("Should be a Left", left.isLeft());
        assertFalse("Should not be a Right", left.isRight());
    }

    /**
     * Test a isLeft() and isRight() for a Right.
     */
    @Test
    public void shouldBeARight() {
        final Either<String, String> right = Either.right(HELLO);

        assertTrue("Should be a Right", right.isRight());
        assertFalse("Should not be a Left", right.isLeft());
    }

    /**
     * Test a get() for a Left.
     */
    @Test
    public void shouldGetEmptyLeft() {
        final Optional<String> optional = Either.<String, String>left(LEFT_VALUE).get();

        assertTrue("Should be empty", optional.isEmpty());
    }

    /**
     * Test a get() for a Right.
     */
    @Test
    public void shouldGetHello() {
        final Optional<String> optional = Either.<String, String>right(HELLO).get();

        assertTrue("Should be present", optional.isPresent());
        assertEquals("Should have the value of HELLO", HELLO, optional.get());
    }

    /**
     * Test a defaulted getOrElse() for a Left.
     */
    @Test
    public void shouldGetTheDefaultedValue() {
        final String value = Either.<String, String>left(LEFT_VALUE).getOrElse(GOODBYE);

        assertNotEquals("Should not have a defaulted value of LEFT_VALUE", LEFT_VALUE, value);
        assertEquals("Should have a defaulted value of GOODBYE", GOODBYE, value);
    }

    /**
     * Test a defaulted getOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheDefaultedValue() {
        final String value = Either.<String, String>right(HELLO).getOrElse(GOODBYE);

        assertNotEquals("Should not have a defaulted value of GOODBYE", GOODBYE, value);
        assertEquals("Should have a value of HELLO", HELLO, value);
    }

    /**
     * Test a supplied getOrElse() for a Left.
     */
    @Test
    public void shouldGetTheSuppliedValue() {
        final String value = Either.<String, String>left(LEFT_VALUE).getOrElse(() -> GOODBYE);

        assertNotEquals("Should not have a supplied value of LEFT_VALUE", LEFT_VALUE, value);
        assertEquals("Should have a supplied value of GOODBYE", GOODBYE, value);
    }

    /**
     * Test a supplied getOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheSuppliedValue() {
        final String value = Either.<String, String>right(HELLO).getOrElse(() -> GOODBYE);

        assertNotEquals("Should not have a supplied value of GOODBYE", GOODBYE, value);
        assertEquals("Should have a value of HELLO", HELLO, value);
    }

    /**
     * Test a getLeft() for a Left.
     */
    @Test
    public void shouldGetLeftValue() {
        final Optional<String> optional = Either.<String, String>left(LEFT_VALUE).getLeft();

        assertTrue("Should be present", optional.isPresent());
        assertEquals("Should have the value of LEFT_VALUE", LEFT_VALUE, optional.get());
    }

    /**
     * Test a getLeft() for a Right.
     */
    @Test
    public void shouldGetEmptyRight() {
        final Optional<String> optional = Either.<String, String>right(HELLO).getLeft();

        assertTrue("Should be empty", optional.isEmpty());
    }

    /**
     * Test a defaulted getLeftOrElse() for a Left.
     */
    @Test
    public void shouldGetTheDefaultedLeftValue() {
        final String value = Either.<String, String>left(LEFT_VALUE).getLeftOrElse(LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a defaulted value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
        assertEquals("Should have a value of LEFT_VALUE", LEFT_VALUE, value);
    }

    /**
     * Test a defaulted getLeftOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheDefaultedLeftValue() {
        final String value = Either.<String, String>right(HELLO).getLeftOrElse(LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a defaulted value of HELLO", HELLO, value);
        assertEquals("Should have a defaulted value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
    }

    /**
     * Test a supplied getLeftOrElse() for a Left.
     */
    @Test
    public void shouldGetTheSuppliedLeftValue() {
        final String value = Either.<String, String>left(LEFT_VALUE).getLeftOrElse(() -> LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a supplied value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
        assertEquals("Should have a value of LEFT_VALUE", LEFT_VALUE, value);
    }

    /**
     * Test a supplied getLeftOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheSuppliedLeftValue() {
        final String value = Either.<String, String>right(HELLO).getLeftOrElse(() -> LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a supplied value of HELLO", HELLO, value);
        assertEquals("Should have a supplied value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
    }

    /**
     * Test a peek() for a Left.
     */
    @Test
    public void shouldNotConsumeTheValue() {
        final Consumer<String> consumer = (String value) -> {
            assertTrue("Peek consumer should not be invoked", false);
        };

        final Either<String, String> either = Either.<String, String>left(LEFT_VALUE).peek(consumer);

        assertTrue("Should return a Left", either.isLeft());
        assertEquals("Should return a value of LEFT_VALUE", LEFT_VALUE, either.getLeft().get());
    }

    /**
     * Test a peek() for a Right.
     */
    @Test
    public void shouldConsumeTheValue() {
        final Consumer<String> consumer = (String value) -> {
            assertEquals("Should consume a value of HELLO", HELLO, value);
        };

        final Either<String, String> either = Either.<String, String>right(HELLO).peek(consumer);

        assertTrue("Should return a Right", either.isRight());
        assertEquals("Should return a value of HELLO", HELLO, either.get().get());
    }

    /**
     * Test a peekLeft() for a Left.
     */
    @Test
    public void shouldConsumeTheLeftValue() {
        final Consumer<String> consumer = (String value) -> {
            assertEquals("Should consume a value of LEFT_VALUE", LEFT_VALUE, value);
        };

        final Either<String, String> either = Either.<String, String>left(LEFT_VALUE).peekLeft(consumer);

        assertTrue("Should return a Left", either.isLeft());
        assertEquals("Should return a value of LEFT_VALUE", LEFT_VALUE, either.getLeft().get());
    }

    /**
     * Test a peekLeft() for a Right.
     */
    @Test
    public void shouldNotConsumeTheLeftValue() {
        final Consumer<String> consumer = (String value) -> {
            assertTrue("Peek left consumer should not be invoked", false);
        };

        final Either<String, String> either = Either.<String, String>right(HELLO).peekLeft(consumer);

        assertTrue("Should return a Right", either.isRight());
        assertEquals("Should return a value of HELLO", HELLO, either.get().get());
    }

    /**
     * Test a biPeek() for a Left.
     */
    @Test
    public void shouldUseTheLeftConsumer() {
        final Consumer<String> consumerL = (String value) -> {
            assertEquals("Should consume a value of LEFT_VALUE", LEFT_VALUE, value);
        };

        final Consumer<String> consumerR = (String value) -> {
            assertTrue("Right consumer should not be invoked", false);
        };

        final Either<String, String> either = Either.<String, String>left(LEFT_VALUE).biPeek(consumerL, consumerR);

        assertTrue("Should return a Left", either.isLeft());
        assertEquals("Should return a value of LEFT_VALUE", LEFT_VALUE, either.getLeft().get());
    }

    /**
     * Test a biPeek() for a Right.
     */
    @Test
    public void shouldUseTheRightConsumer() {
        final Consumer<String> consumerL = (String value) -> {
            assertTrue("Left consumer should not be invoked", false);
        };

        final Consumer<String> consumerR = (String value) -> {
            assertEquals("Should consume a value of HELLO", HELLO, value);
        };

        final Either<String, String> either = Either.<String, String>right(HELLO).biPeek(consumerL, consumerR);

        assertTrue("Should return a Right", either.isRight());
        assertEquals("Should return a value of HELLO", HELLO, either.get().get());
    }

    /**
     * Test a toString() for a Left.
     */
    @Test
    public void shouldShowALeftString() {
        assertEquals(String.format("Either.left[%s]", LEFT_VALUE), Either.<String, String>left(LEFT_VALUE).toString());
    }

    /**
     * Test a toString() for a Right.
     */
    @Test
    public void shouldShowARightString() {
        assertEquals(String.format("Either.right[%s]", HELLO), Either.<String, String>right(HELLO).toString());
    }

    /**
     * Test a equals() for a Left.
     */
    @Test
    public void shouldEqualALeftOfEqualValue() {
        final Either<String, String> left1 = Either.left(LEFT_VALUE);
        final Either<String, String> left2 = Either.left(LEFT_VALUE);
        final Either<String, String> leftDefault = Either.left(LEFT_VALUE_DEFAULT);
        final Either<String, String> leftInRight = Either.right(LEFT_VALUE);
        final Either<String, String> right = Either.right(HELLO);

        assertTrue("Should equal itself", left1.equals(left1));
        assertEquals("Should equal itself", left1, left1);

        assertTrue("Should equal another Left of equal value", left1.equals(left2));
        assertEquals("Should equal another Left of equal value", left1, left2);

        assertFalse("Should not equal another Left of different value", left1.equals(leftDefault));
        assertNotEquals("Should not equal another Left of different value", left1, leftDefault);

        assertFalse("Should not equal a Right of equal value", left1.equals(leftInRight));
        assertNotEquals("Should not equal a Right of equal value", left1, leftInRight);

        assertFalse("Should not equal a Right of different value", left1.equals(right));
        assertNotEquals("Should not equal a Right of different value", left1, right);

        assertFalse("Should not equal a null", left1.equals(null));
        assertNotEquals("Should not equal a null", left1, null);

        // assertFalse("Should not equal another type", left1.equals(LEFT_VALUE));
        assertNotEquals("Should not equal another type", left1, LEFT_VALUE);
    }

    /**
     * Test a equals() for a Right.
     */
    @Test
    public void shouldEqualARightOfEqualValue() {
        final Either<String, String> hello1 = Either.right(HELLO);
        final Either<String, String> hello2 = Either.right(HELLO);
        final Either<String, String> goodbye = Either.right(GOODBYE);
        final Either<String, String> helloInLeft = Either.left(HELLO);
        final Either<String, String> left = Either.left(LEFT_VALUE);

        assertTrue("Should equal itself", hello1.equals(hello1));
        assertEquals("Should equal itself", hello1, hello1);

        assertTrue("Should equal another Right of equal value", hello1.equals(hello2));
        assertEquals("Should equal another Right of equal value", hello1, hello2);

        assertFalse("Should not equal another Right of different value", hello1.equals(goodbye));
        assertNotEquals("Should not equal another Right of different value", hello1, goodbye);

        assertFalse("Should not equal a Left of equal value", hello1.equals(helloInLeft));
        assertNotEquals("Should not equal a Left of equal value", hello1, helloInLeft);

        assertFalse("Should not equal a Left of different value", hello1.equals(left));
        assertNotEquals("Should not equal a Left of different value", hello1, left);

        assertFalse("Should not equal a null", hello1.equals(null));
        assertNotEquals("Should not equal a null", hello1, null);

        // assertFalse("Should not equal another type", hello1.equals(HELLO));
        assertNotEquals("Should not equal another type", hello1, HELLO);
    }

    /**
     * Test map() for a Left.
     */
    @Test
    public void shouldNotMapALeft() {
        final Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should not map a Left", LEFT_VALUE,
                Either.<String, String>left(LEFT_VALUE).map(fxn.apply(HELLO)).getLeft().get());
    }

    /**
     * Test map() for a Right.
     */
    @Test
    public void shouldMapARight() {
        final Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should map a Right", String.format("%s, %s", HELLO, WORLD),
                Either.<String, String>right(WORLD).map(fxn.apply(HELLO)).get().get());
    }

    /**
     * Test fmap() for a Left.
     */
    @Test
    public void shouldNotFmapALeft() {
        final Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should not fmap a Left", LEFT_VALUE,
                Either.fmap(fxn.apply(HELLO), Either.<String, String>left(LEFT_VALUE)).getLeft().get());
    }

    /**
     * Test fmap() for a Right.
     */
    @Test
    public void shouldFmapARight() {
        final Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should fmap a Right", String.format("%s, %s", HELLO, WORLD),
                Either.fmap(fxn.apply(HELLO), Either.<String, String>right(WORLD)).get().get());
    }

    /**
     * Test that Left satisfies the Functor laws.
     *   See Hutton; Programming in Haskell; p156
     *
     * <p>For the identity function, id, and two mapping functions g and h:
     * {@code}
     * fmap id = id<br>
     * fmap (g . h) = fmap g . fmap h
     * {code}
     */
    @Test
    public void shouldSatisfyFunctorLawsForALeft() {
        final Either<String, Integer> left = Either.<String, Integer>left(LEFT_VALUE);

        // Identity function
        final Function<Integer, Integer> id1 = x -> x;
        final Function<Either<String, Integer>, Either<String, Integer>> id2 = x -> x;

        assertEquals("Should satify Functor law #1 with map", id2.apply(left), left.map(id1));
        assertEquals("Should satify Functor law #1 with fmap", id2.apply(left), Either.fmap(id1, left));

        // Two mapping functions
        final Function<Integer, Integer> g = x -> x + 3;
        final Function<Integer, Integer> h = x -> 256 * x;

        assertEquals("Should satify Functor law #2 with map", left.map(g.compose(h)), left.map(h).map(g));
        assertEquals("Should satify Functor law #2 with fmap", Either.fmap(g.compose(h), left),
                Either.fmap(g, Either.fmap(h, left)));
    }

    /**
     * Test that Right satisfies the Functor laws.
     *   See Hutton; Programming in Haskell; p156
     *
     * <p>For the identity function, id, and two mapping functions g and h:
     * {@code}
     * fmap id = id
     * fmap (g . h) = fmap g . fmap h
     * {code}
     */
    @Test
    public void shouldSatisfyFunctorLawsForARight() {
        final int I = 7;
        final Either<String, Integer> right = Either.<String, Integer>right(I);

        // Identity function
        final Function<Integer, Integer> id1 = x -> x;
        final Function<Either<String, Integer>, Either<String, Integer>> id2 = x -> x;

        assertEquals("Should satify Functor law #1 with map", id2.apply(right), right.map(id1));
        assertEquals("Should satify Functor law #1 with fmap", id2.apply(right), Either.fmap(id1, right));

        // Two mapping functions
        final Function<Integer, Integer> g = x -> x + 3;
        final Function<Integer, Integer> h = x -> 256 * x;

        assertEquals("Should satify Functor law #2 with map", right.map(g.compose(h)), right.map(h).map(g));
        assertEquals("Should satify Functor law #2 with fmap", Either.fmap(g.compose(h), right),
                Either.fmap(g, Either.fmap(h, right)));
    }

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
