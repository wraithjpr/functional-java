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
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests demonstrating use of applicatives.
 */
public class ApplicativesInUseTest {
    /**
     * Example of using applicative Either to apply a simple add function.
     */
    @Test
    public void arithmeticWithApplicative() {
        // add :: a -> a -> a
        // add x y = x + y

        final int X = 11;
        final int Y = 21;
        final int Z = 32;

        final Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
        final Either<String, Integer> fx = Either.right(X);
        final Either<String, Integer> fy = Either.right(Y);

        final Either<String, Function<Integer, Function<Integer, Integer>>> fadd = Either.pure(add);
        final Either<String, Function<Integer, Integer>> faddx = fadd.apply(fx);
        final Either<String, Integer> faddxy = faddx.apply(fy);

        assertEquals("should be 11 + 12 = 32", Z, faddxy.get().get().intValue());

        assertEquals(Either.right(24), Either.pure(add).ap(Either.right(8)).ap(Either.right(16)));
        assertTrue(Either.right(24).equals(Either.pure(add).ap(Either.right(8)).ap(Either.right(16))));
    }

    /**
     * We can do unit test assertions in the applicative style.
     */
    @Test
    public void assertWithApplicatives() {
        // add :: a -> a -> a
        // add x y = x + y
        final Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        // assertEquals :: String -> int -> int -> ()
        final Function<String, Function<Integer, Function<Integer, Integer>>> assertEqualsCurried
            = message -> expected -> actual -> {
                assertEquals(message, expected, actual);
                return actual;
            };

        // test :: int -> int -> ()
        final Function<Integer, Function<Integer, Integer>> test
            = assertEqualsCurried.apply("24 + 8 should be 32");

        final int expected = 32;
        final int actual = add.apply(24).apply(8);

        Either.pure(test).ap(Either.right(expected)).ap(Either.right(actual));

    }

    /**
     * We can validate using applicatives.
     */
    @Test
    public void validateWithApplicatives() {
        // Set up local variables and functions
        final int VALID_VALUE = 20;
        final int INVALID_VALUE = 9;

        final Predicate<Integer> check = x -> x > 10;
        final String message = "Value of %d should be > 10";

        final Function<Predicate<Integer>, Function<String, Function<Integer, Either<String, Integer>>>> validateCurried
            = pred -> errMsg -> x -> pred.test(x) ? Either.right(x) : Either.left(String.format(errMsg, x));

        final Function<Integer, Either<String, Integer>> validateFxn = validateCurried.apply(check).apply(message);

        final class AnX {
            final int value;

            AnX(final int value) {
                this.value = value;
            }

            int getX() {
                return value;
            }
        }

        final Function<Integer, AnX> makeAnX = (Integer x) -> {
            return new AnX(x);
        };

        // Run the test for valid value
        final Either<String, Integer> validatedX = validateFxn.apply(VALID_VALUE);
        final Either<String, AnX> eitherAnX = Either.<String, Function<Integer, AnX>>pure(makeAnX).ap(validatedX);

        // Verify assertions
        assertEquals("x should be 20", 20, VALID_VALUE);
        assertTrue("Should be a Right", eitherAnX.isRight());
        assertEquals(String.format("Value should be %s", VALID_VALUE), VALID_VALUE, eitherAnX.getOrElse(new AnX(-1)).getX());

        // Run the test for an invalid value
        final Either<String, Integer> invalidatedX = validateFxn.apply(INVALID_VALUE);
        final Either<String, AnX> error = Either.<String, Function<Integer, AnX>>pure(makeAnX).ap(invalidatedX);

        // Verify assertions
        assertEquals("x should be 9", 9, INVALID_VALUE);
        assertTrue("Should be a Left", error.isLeft());
        assertEquals("Should be an error message", String.format(message, INVALID_VALUE), error.getLeftOrElse(""));
    }
}
