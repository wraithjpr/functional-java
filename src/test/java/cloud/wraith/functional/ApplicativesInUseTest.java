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

        final Function<String, Function<Integer, Function<Integer, Integer>>> assertEqualsCurried = message -> expected -> actual -> {
            assertEquals(message, expected, actual);
            return actual;
        };

        final Either<Object, Function<Integer, Integer>> test = Either.pure(assertEqualsCurried)
            .ap(Either.right("should be 32"))
            .ap(Either.right(32));

        test.ap(
            Either.pure(add).ap(Either.right(24)).ap(Either.right(8))
        );
    }

    /**
     * We can validate using applicatives.
     */
    @Ignore
    public void validateWithApplicatives() {
        Predicate<Integer> check = x -> x > 10;
        String message = "Value of %d should be > 10";

        Function<Predicate<Integer>, Function<String, Function<Integer, Either<String, Integer>>>> validateCurried =
            pred -> errMsg -> x -> pred.test(x) ? Either.right(x) : Either.left(String.format(errMsg, x));

        Function<Integer, Either<String, Integer>> validate = validateCurried.apply(check).apply(message);

        Either<String, Integer> validatedX = Either.pure(20);    //TODO we need a flatmap/bind
    }
}
