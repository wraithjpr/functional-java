package cloud.wraith.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Test;

import cloud.wraith.functional.Either.Left;
import cloud.wraith.functional.Either.Right;

/**
 * Unit test for Either class.
 */
public class EitherTest {
    private final static String GOODBYE = "goodbye";
    private final static String HELLO = "hello";
    private final static String WORLD = "world";
    private final static String LEFT_VALUE = "I am left-handed";
    private final static String LEFT_VALUE_DEFAULT = "I am left-handed by default";

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
        Either<String, String> left = Either.left(LEFT_VALUE);

        assertTrue("Should be a Left", left.isLeft());
        assertFalse("Should not be a Right", left.isRight());
    }

    /**
     * Test a isLeft() and isRight() for a Right.
     */
    @Test
    public void shouldBeARight() {
        Either<String, String> right = Either.right(HELLO);

        assertTrue("Should be a Right", right.isRight());
        assertFalse("Should not be a Left", right.isLeft());
    }

    /**
     * Test a get() for a Left.
     */
    @Test
    public void shouldGetEmptyLeft() {
        Optional<String> optional = Either.<String, String>left(LEFT_VALUE).get();

        assertTrue("Should be empty", optional.isEmpty());
    }

    /**
     * Test a get() for a Right.
     */
    @Test
    public void shouldGetHello() {
        Optional<String> optional = Either.<String, String>right(HELLO).get();

        assertTrue("Should be present", optional.isPresent());
        assertEquals("Should have the value of HELLO", HELLO, optional.get());
    }

    /**
     * Test a defaulted getOrElse() for a Left.
     */
    @Test
    public void shouldGetTheDefaultedValue() {
        String value = Either.<String, String>left(LEFT_VALUE).getOrElse(GOODBYE);

        assertNotEquals("Should not have a defaulted value of LEFT_VALUE", LEFT_VALUE, value);
        assertEquals("Should have a defaulted value of GOODBYE", GOODBYE, value);
    }

    /**
     * Test a defaulted getOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheDefaultedValue() {
        String value = Either.<String, String>right(HELLO).getOrElse(GOODBYE);

        assertNotEquals("Should not have a defaulted value of GOODBYE", GOODBYE, value);
        assertEquals("Should have a value of HELLO", HELLO, value);
    }

    /**
     * Test a supplied getOrElse() for a Left.
     */
    @Test
    public void shouldGetTheSuppliedValue() {
        String value = Either.<String, String>left(LEFT_VALUE).getOrElse(() -> GOODBYE);

        assertNotEquals("Should not have a supplied value of LEFT_VALUE", LEFT_VALUE, value);
        assertEquals("Should have a supplied value of GOODBYE", GOODBYE, value);
    }

    /**
     * Test a supplied getOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheSuppliedValue() {
        String value = Either.<String, String>right(HELLO).getOrElse(() -> GOODBYE);

        assertNotEquals("Should not have a supplied value of GOODBYE", GOODBYE, value);
        assertEquals("Should have a value of HELLO", HELLO, value);
    }

    /**
     * Test a getLeft() for a Left.
     */
    @Test
    public void shouldGetLeftValue() {
        Optional<String> optional = Either.<String, String>left(LEFT_VALUE).getLeft();

        assertTrue("Should be present", optional.isPresent());
        assertEquals("Should have the value of LEFT_VALUE", LEFT_VALUE, optional.get());
    }

    /**
     * Test a getLeft() for a Right.
     */
    @Test
    public void shouldGetEmptyRight() {
        Optional<String> optional = Either.<String, String>right(HELLO).getLeft();

        assertTrue("Should be empty", optional.isEmpty());
    }

    /**
     * Test a defaulted getLeftOrElse() for a Left.
     */
    @Test
    public void shouldGetTheDefaultedLeftValue() {
        String value = Either.<String, String>left(LEFT_VALUE).getLeftOrElse(LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a defaulted value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
        assertEquals("Should have a value of LEFT_VALUE", LEFT_VALUE, value);
    }

    /**
     * Test a defaulted getLeftOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheDefaultedLeftValue() {
        String value = Either.<String, String>right(HELLO).getLeftOrElse(LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a defaulted value of HELLO", HELLO, value);
        assertEquals("Should have a defaulted value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
    }

    /**
     * Test a supplied getLeftOrElse() for a Left.
     */
    @Test
    public void shouldGetTheSuppliedLeftValue() {
        String value = Either.<String, String>left(LEFT_VALUE).getLeftOrElse(() -> LEFT_VALUE_DEFAULT);

        assertNotEquals("Should not have a supplied value of LEFT_VALUE_DEFAULT", LEFT_VALUE_DEFAULT, value);
        assertEquals("Should have a value of LEFT_VALUE", LEFT_VALUE, value);
    }

    /**
     * Test a supplied getLeftOrElse() for a Right.
     */
    @Test
    public void shouldNotGetTheSuppliedLeftValue() {
        String value = Either.<String, String>right(HELLO).getLeftOrElse(() -> LEFT_VALUE_DEFAULT);

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

        Either<String, String> either = Either.<String, String>left(LEFT_VALUE).peek(consumer);

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

        Either<String, String> either = Either.<String, String>right(HELLO).peek(consumer);

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

        Either<String, String> either = Either.<String, String>left(LEFT_VALUE).peekLeft(consumer);

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

        Either<String, String> either = Either.<String, String>right(HELLO).peekLeft(consumer);

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

        Either<String, String> either = Either.<String, String>left(LEFT_VALUE).biPeek(consumerL, consumerR);

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

        Either<String, String> either = Either.<String, String>right(HELLO).biPeek(consumerL, consumerR);

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
        Either<String, String> left1 = Either.left(LEFT_VALUE);
        Either<String, String> left2 = Either.left(LEFT_VALUE);
        Either<String, String> leftDefault = Either.left(LEFT_VALUE_DEFAULT);
        Either<String, String> leftInRight = Either.right(LEFT_VALUE);
        Either<String, String> right = Either.right(HELLO);

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
        Either<String, String> hello1 = Either.right(HELLO);
        Either<String, String> hello2 = Either.right(HELLO);
        Either<String, String> goodbye = Either.right(GOODBYE);
        Either<String, String> helloInLeft = Either.left(HELLO);
        Either<String, String> left = Either.left(LEFT_VALUE);

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
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should not map a Left", LEFT_VALUE,
                Either.<String, String>left(LEFT_VALUE).map(fxn.apply(HELLO)).getLeft().get());
    }

    /**
     * Test map() for a Right.
     */
    @Test
    public void shouldMapARight() {
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should map a Right", String.format("%s, %s", HELLO, WORLD),
                Either.<String, String>right(WORLD).map(fxn.apply(HELLO)).get().get());
    }

    /**
     * Test fmap() for a Left.
     */
    @Test
    public void shouldNotFmapALeft() {
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should not fmap a Left", LEFT_VALUE,
                Either.fmap(fxn.apply(HELLO), Either.<String, String>left(LEFT_VALUE)).getLeft().get());
    }

    /**
     * Test fmap() for a Right.
     */
    @Test
    public void shouldFmapARight() {
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should fmap a Right", String.format("%s, %s", HELLO, WORLD),
                Either.fmap(fxn.apply(HELLO), Either.<String, String>right(WORLD)).get().get());
    }

    /**
     * Test that Left satisfies the Functor laws.
     */
    @Test
    public void shouldSatisfyFunctorLawsForALeft() {
        Either<String, Integer> left = Either.<String, Integer>left(LEFT_VALUE);

        // Identity function
        Function<Integer, Integer> id1 = x -> x;
        Function<Either<String, Integer>, Either<String, Integer>> id2 = x -> x;

        assertEquals("Should satify Functor law #1 with map", id2.apply(left), left.map(id1));
        assertEquals("Should satify Functor law #1 with fmap", id2.apply(left), Either.fmap(id1, left));

        // Two mapping functions
        Function<Integer, Integer> g = x -> x + 3;
        Function<Integer, Integer> h = x -> 256 * x;

        assertEquals("Should satify Functor law #2 with map", left.map(g.compose(h)), left.map(h).map(g));
        assertEquals("Should satify Functor law #2 with fmap", Either.fmap(g.compose(h), left),
                Either.fmap(g, Either.fmap(h, left)));
    }

    /**
     * Test that Right satisfies the Functor laws.
     *
     * <p>
     * For the identity function, id, and two mapping functions g and h:
     * <p>
     * &nbsp;&nbsp;fmap id = id<br>
     * &nbsp;&nbsp;fmap (g . h) = fmap g . fmap h
     */
    @Test
    public void shouldSatisfyFunctorLawsForARight() {
        final int I = 7;
        Either<String, Integer> right = Either.<String, Integer>right(I);

        // Identity function
        Function<Integer, Integer> id1 = x -> x;
        Function<Either<String, Integer>, Either<String, Integer>> id2 = x -> x;

        assertEquals("Should satify Functor law #1 with map", id2.apply(right), right.map(id1));
        assertEquals("Should satify Functor law #1 with fmap", id2.apply(right), Either.fmap(id1, right));

        // Two mapping functions
        Function<Integer, Integer> g = x -> x + 3;
        Function<Integer, Integer> h = x -> 256 * x;

        assertEquals("Should satify Functor law #2 with map", right.map(g.compose(h)), right.map(h).map(g));
        assertEquals("Should satify Functor law #2 with fmap", Either.fmap(g.compose(h), right),
                Either.fmap(g, Either.fmap(h, right)));
    }

}
