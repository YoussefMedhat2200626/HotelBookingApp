package hotelPackage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuiteTest {
    private Suite suite;

    @BeforeEach
    void setUp() {
        suite = new Suite(201, 300.0, true);
    }

    @Test
    void testGetRoomNumber() {
        assertEquals(201, suite.getRoomNumber());
    }

    @Test
    void testGetPricePerNight() {
        assertEquals(300.0, suite.getPricePerNight(), 0.01);
    }

    @Test
    void testIsAvailableInitially() {
        assertTrue(suite.isAvailable());
    }

    @Test
    void testBookAndRelease() {
        suite.book();
        assertFalse(suite.isAvailable());

        suite.release();
        assertTrue(suite.isAvailable());
    }

    @Test
    void testCompareTo() {
        Suite cheaperSuite = new Suite(202, 200.0, false);
        assertTrue(suite.compareTo(cheaperSuite) > 0);
    }

    @Test
    void testHasJacuzzi() {
        assertTrue(suite.hasJacuzzi());
    }

    @Test
    void testGetRoomType() {
        assertEquals("Suite", suite.getRoomType());
    }

    @Test
    void testCalculateCost() {
        assertEquals(900.0, suite.calculateCost(3), 0.01);
    }

    @Test
    void testToStringFormat() {
        String expected = "Suite{roomNumber=201, pricePerNight=300.0, hasJacuzzi=true, isAvailable=true}";
        assertEquals(expected, suite.toString());
    }
}
