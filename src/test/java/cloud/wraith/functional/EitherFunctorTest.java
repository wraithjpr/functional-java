package cloud.wraith.functional;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

/**
 * Unit test for Either class.
 */
public class EitherFunctorTest {
    private static final String HELLO = "hello";
    private static final String WORLD = "world";
    private static final String LEFT_VALUE = "I am left-handed";

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
}
