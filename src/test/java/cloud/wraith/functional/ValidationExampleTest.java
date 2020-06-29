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
public class ValidationExampleTest {

    /**
     * We can validate using applicatives.
     */
    @Test
    public void shouldValdidateARequestBodyAndConstructStreamData() {

        // constructorFxn :: datetimestamp -> siteId -> activityType -> currency -> count -> value -> Activity

        assertTrue(false);      //Force a test fail
    }
}
