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
     * Test map() for a Right.
     */
    @Test
    public void shouldMapARight() {
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should map a Right", String.format("%s, %s", HELLO, WORLD),
                Either.<String, String>right(WORLD).map(fxn.apply(HELLO)).get().get());
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
     * Test fmap() for a Right.
     */
    @Test
    public void shouldFmapARight() {
        Function<String, Function<String, String>> fxn = s1 -> s2 -> String.format("%s, %s", s1, s2);

        assertEquals("Should fmap a Right", String.format("%s, %s", HELLO, WORLD),
                Either.fmap(fxn.apply(HELLO), Either.<String, String>right(WORLD)).get().get());
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

}