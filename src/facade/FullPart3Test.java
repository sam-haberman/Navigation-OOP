package facade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.*;

import connections.*;

/**
 * An extended collection of tests to verify the correctness of your code.
 * 
 * The test suite uses the facade to invoke methods against your code.
 * As for the facade, it assumes that you have at least a class Location,
 * a class Road and a class Route. You must add import statements in the
 * beginning of this file such that the test suite can use them.
 */
class FullPart3Test {

	public static final float SPEED_OF_LIGHT = 299792458.0F;

	private static int actualScore = 0;
	private static int maxScore = 0;

	Facade theFacade = new Facade() {};

	private static double[] coord_10_20, coord_15_60;
	
	private static Location leuven, antwerp, brussels, gent, hasselt;

	private static Road someRoad, t1_leuven_antwerp, t2_leuven_brussels, 
		t4_brussels_antwerp, t5_leuven_gent, c1_circularRoad_antwerp, 
		c2_circularRoad_brussels, O1_brussels_gent_oneWay, O2_hasselt_gent_oneWay, a1_gent_brussels_alter;
	
	private static Route emptyRoute_leuven, route_leuven_antwerp_brussels_gent, circularRoute_antwerp_leuven_brussels_brussels_antwerp;

	@BeforeAll
	static void setUpBeforeAll() throws Exception {
		coord_10_20 = new double[] { 10.0, 20.0 };
		coord_15_60 = new double[] { 15.3, 60.6 };
	}

	@AfterAll
	static void tearDownAfterAll() throws Exception {
		System.out.println("Final score: " + Integer.toString(actualScore) + " / " + Integer.toString(maxScore));
	}

	@BeforeEach
	void setUpBeforeEach() throws Exception {
		leuven = theFacade.createLocation(coord_10_20, "Leuven");
		antwerp = theFacade.createLocation(coord_15_60, "Antwerp");
		brussels = theFacade.createLocation(new double[] { 20.0, 30.0 }, "Brussels");
		gent = theFacade.createLocation(new double[] { 15.0, 17.5 }, "Gent");
		hasselt = theFacade.createLocation(new double[] { 22.2, 42.5 }, "Hasselt");
		someRoad = null;
		t1_leuven_antwerp = theFacade.createTwoWayRoad("T1", leuven, antwerp, 1111, 15.5F, 10.66F);
		t2_leuven_brussels = theFacade.createTwoWayRoad("T2", leuven, brussels, 1500, 16.66F, 12.22F);
		t4_brussels_antwerp = theFacade.createTwoWayRoad("T4", brussels, antwerp, 55, 90.0F, 50.0F);
		t5_leuven_gent = theFacade.createTwoWayRoad("T5", leuven, gent, 1500, 16.66F, 12.22F);
		c1_circularRoad_antwerp = theFacade.createTwoWayRoad("C1", antwerp, antwerp, 55, 90.0F, 50.0F);
		c2_circularRoad_brussels = theFacade.createTwoWayRoad("C2", brussels, brussels, 40, 30.0F, 20.0F);
		O1_brussels_gent_oneWay = theFacade.createOneWayRoad("O1", brussels, gent, 70, 100.0F, 60.0F);
		O2_hasselt_gent_oneWay = theFacade.createOneWayRoad("O2", hasselt, gent, 1500, 16.66F, 12.22F);
		a1_gent_brussels_alter = theFacade.createAlternatingRoad("A1", gent, brussels, 1500, 16.66F, 12.22F);
		emptyRoute_leuven = theFacade.createRoute(leuven);
		route_leuven_antwerp_brussels_gent =
				theFacade.createRoute(leuven, t1_leuven_antwerp, t4_brussels_antwerp, O1_brussels_gent_oneWay);
		circularRoute_antwerp_leuven_brussels_brussels_antwerp =
				theFacade.createRoute(antwerp, t1_leuven_antwerp, t2_leuven_brussels, c2_circularRoad_brussels, t4_brussels_antwerp);
	}

	@AfterEach
	void tearDownAfterEach() throws Exception {
		// All roads must be terminated, such that their codes can be re-used
		// for each test.
		if (someRoad != null)
			theFacade.terminateRoad(someRoad);
		theFacade.terminateRoad(t1_leuven_antwerp);
		theFacade.terminateRoad(t2_leuven_brussels);
		theFacade.terminateRoad(O1_brussels_gent_oneWay);
		theFacade.terminateRoad(t4_brussels_antwerp);
		theFacade.terminateRoad(t5_leuven_gent);
		theFacade.terminateRoad(c1_circularRoad_antwerp);
		theFacade.terminateRoad(c2_circularRoad_brussels);
		theFacade.terminateRoad(O2_hasselt_gent_oneWay);
		theFacade.terminateRoad(a1_gent_brussels_alter);
	}

	/******************
	 * Location tests *
	 ******************/

	@Test
	void createLocation_LegalCase() throws Exception {
		maxScore += 6;
		Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, "Leuven");
		assertEquals(10.0, theFacade.getLocationCoordinates(theLocation)[0], 0.05);
		assertEquals(20.0, theFacade.getLocationCoordinates(theLocation)[1], 0.05);
		assertEquals("Leuven", theFacade.getLocationAddress(theLocation));
		actualScore += 6;
	}

	@Test
	void createLocation_IllegalEndPoints() throws Exception {
		maxScore += 10;
		try {
			theFacade.createLocation(null, "Leuven");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createLocation(new double[] { 1.0 }, "Leuven");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createLocation(new double[] { 1.0, 2.0, 3.0 }, "Leuven");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createLocation(new double[] { Double.POSITIVE_INFINITY, 2.0 }, "Leuven");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.createLocation(new double[] { Double.NaN, 2.0 }, "Leuven");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void createLocation_LeakTest() throws Exception {
		maxScore += 10;
		double[] coordinatesToUse = new double[] { 10.0, 20.0 };
		Location theLocation = theFacade.createLocation(coordinatesToUse, "Leuven");
		coordinatesToUse[0] = Float.POSITIVE_INFINITY;
		assertEquals(10.0, theFacade.getLocationCoordinates(theLocation)[0], 0.05);
		actualScore += 10;
	}

	@Test
	void getCoordinates_LeakTest() throws Exception {
		maxScore += 10;
		Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, "Leuven");
		double[] theCoordinates = theFacade.getLocationCoordinates(theLocation);
		theCoordinates[0] = Float.POSITIVE_INFINITY;
		assertEquals(10.0, theFacade.getLocationCoordinates(theLocation)[0], 0.05);
		actualScore += 10;
	}

	@Test
	void createLocation_AllAddressCharacterTest() throws Exception {
		maxScore += 5;
		Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 },
				"ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,");
		assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,",
				theFacade.getLocationAddress(theLocation));
		actualScore += 5;
	}
	
	static boolean isWellFormedAddress(String address) {
		if (address.length() <2)
			return false;
		if (! Character.isUpperCase(address.charAt(0)))
			return false;
		for (int index=1; index < address.length(); index++)
			if ( (! Character.isLetter(address.charAt(index))) &&
				 (! Character.isDigit(address.charAt(index))) &&
				 (address.charAt(index) != ' ') &&
				 (address.charAt(index) != ',') )
				return false;
		return true;
	}

	@Test
	void createLocation_IllegalAddress() throws Exception {
		maxScore += 12;
		try {
			Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, null);
			assertTrue(isWellFormedAddress(theFacade.getLocationAddress(theLocation)));
			actualScore += 3;
		} catch (Throwable exc) {
		}
		try {
			Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, "A");
			assertTrue(isWellFormedAddress(theFacade.getLocationAddress(theLocation)));
			actualScore += 3;
		} catch (ModelException exc) {
		}
		try {
			Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, "abc");
			assertTrue(isWellFormedAddress(theFacade.getLocationAddress(theLocation)));
			actualScore += 3;
		} catch (ModelException exc) {
			actualScore += 3;
		}
		try {
			Location theLocation = theFacade.createLocation(new double[] { 10.0, 20.0 }, "Abc%12");
			assertTrue(isWellFormedAddress(theFacade.getLocationAddress(theLocation)));
			actualScore += 3;
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void terminateLocation_SingleCase() throws Exception {
		maxScore += 12;
		Collection<Road> adjoiningRoads_leuven = theFacade.getLocationAllAdjoiningRoads(leuven);
		theFacade.terminateLocation(leuven);
		assertTrue(theFacade.isTerminatedLocation(leuven));
		assertTrue(theFacade.getLocationAllAdjoiningRoads(leuven).isEmpty());
		for (Road road: adjoiningRoads_leuven)
			assertTrue(theFacade.isTerminatedRoad(road));
		actualScore += 12;
	}

	@Test
	void getLocationAdjoiningRoads_LeakTest() throws Exception {
		maxScore += 15;
		Collection<Road> localLeuvenRoads = theFacade.getLocationAllAdjoiningRoads(leuven);
		int nbLocalLeuvenRoads = localLeuvenRoads.size();
		localLeuvenRoads.remove(t1_leuven_antwerp);
		assertFalse(localLeuvenRoads.contains(t1_leuven_antwerp));
		localLeuvenRoads.add(null);
		localLeuvenRoads.add(a1_gent_brussels_alter);
		assertEquals(nbLocalLeuvenRoads+1, localLeuvenRoads.size());
		Collection<Road> leuvenRoads = theFacade.getLocationAllAdjoiningRoads(leuven);
		assertEquals(nbLocalLeuvenRoads, leuvenRoads.size());
		assertTrue(leuvenRoads.contains(t1_leuven_antwerp));
		assertFalse(leuvenRoads.contains(null));
		assertFalse(leuvenRoads.contains(a1_gent_brussels_alter));
		actualScore += 15;
	}

	/**************
	 * Road tests *
	 **************/

	@Test
	void createOneWayRoad_LegalCase() throws Exception {
		maxScore += 10;
		someRoad = theFacade.createOneWayRoad("E4", leuven, antwerp, 1500, 16.66F, 12.22F);
		assertEquals("E4", theFacade.getRoadIdentification(someRoad));
		assertEquals(leuven, theFacade.getEndPoints(someRoad)[0]);
		assertEquals(antwerp, theFacade.getEndPoints(someRoad)[1]);
		assertEquals(1500, theFacade.getRoadLength(someRoad));
		assertEquals(16.66F, theFacade.getRoadSpeedLimit(someRoad), 0.1E-10F);
		assertEquals(12.22F, theFacade.getRoadAverageSpeed(someRoad), 0.1E-10F);
		assertEquals(0.0F, theFacade.getRoadDelayinDirection(someRoad, true), 0.1E-10F);
		assertFalse(theFacade.getRoadIsBlocked(someRoad, true));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(leuven, someRoad));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(antwerp, someRoad));
		actualScore += 10;
	}

	@Test
	void createTwoWayRoad_LegalCase() throws Exception {
		maxScore += 30;
		someRoad = theFacade.createTwoWayRoad("E4", leuven, antwerp, 1500, 16.66F, 12.22F);
		assertEquals("E4", theFacade.getRoadIdentification(someRoad));
		assertEquals(leuven, theFacade.getEndPoints(someRoad)[0]);
		assertEquals(antwerp, theFacade.getEndPoints(someRoad)[1]);
		assertEquals(1500, theFacade.getRoadLength(someRoad));
		assertEquals(16.66F, theFacade.getRoadSpeedLimit(someRoad), 0.1E-10F);
		assertEquals(12.22F, theFacade.getRoadAverageSpeed(someRoad), 0.1E-10F);
		assertEquals(0.0F, theFacade.getRoadDelayinDirection(someRoad, true), 0.1E-10F);
		assertEquals(0.0F, theFacade.getRoadDelayinDirection(someRoad, false), 0.1E-10F);
		assertFalse(theFacade.getRoadIsBlocked(someRoad, true));
		assertFalse(theFacade.getRoadIsBlocked(someRoad, false));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(leuven, someRoad));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(antwerp, someRoad));
		actualScore += 30;
	}

	@Test
	void createAlternatingRoad_LegalCase() throws Exception {
		maxScore += 10;
		someRoad = theFacade.createAlternatingRoad("X4", leuven, antwerp, 1500, 16.66F, 12.22F);
		assertEquals("X4", theFacade.getRoadIdentification(someRoad));
		assertEquals(leuven, theFacade.getEndPoints(someRoad)[0]);
		assertEquals(antwerp, theFacade.getEndPoints(someRoad)[1]);
		assertEquals(1500, theFacade.getRoadLength(someRoad));
		assertEquals(16.66F, theFacade.getRoadSpeedLimit(someRoad), 0.1E-10F);
		assertEquals(12.22F, theFacade.getRoadAverageSpeed(someRoad), 0.1E-10F);
		assertEquals(0.0F, theFacade.getRoadDelayinDirection(someRoad, true), 0.1E-10F);
		assertFalse(theFacade.getRoadIsBlocked(someRoad, true));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(leuven, someRoad));
		assertTrue(theFacade.locationHasAsAdjoiningRoad(antwerp, someRoad));
		actualScore += 10;
	}

	@Test
	void createRoad_LocationsWithAdjoiningRoads() throws Exception {
		maxScore += 10;
		int nbAdjoiningRoadsLeuven = theFacade.getLocationAllAdjoiningRoads(leuven).size();
		int nbAdjoiningRoadsAntwerp = theFacade.getLocationAllAdjoiningRoads(antwerp).size();
		someRoad = theFacade.createTwoWayRoad("X99", leuven, antwerp, 1500, 16.66F, 12.22F);
		assertTrue(theFacade.locationHasAsAdjoiningRoad(leuven, someRoad));
		assertEquals(nbAdjoiningRoadsLeuven+1, theFacade.getLocationAllAdjoiningRoads(leuven).size());
		assertTrue(theFacade.locationHasAsAdjoiningRoad(antwerp, someRoad));
		assertEquals(nbAdjoiningRoadsAntwerp+1, theFacade.getLocationAllAdjoiningRoads(antwerp).size());
		actualScore += 10;
	}

	@Test
	void createRoad_IllegalIdentification() throws Exception {
		maxScore += 5;
		try {
			theFacade.createTwoWayRoad("6", leuven, antwerp, 250, 14.44F, 10.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_IllegalLength() throws Exception {
		maxScore += 5;
		someRoad = theFacade.createTwoWayRoad("X2", leuven, antwerp, -10, 5.55F, 3.6F);
		assertTrue(theFacade.getRoadLength(someRoad) > 0);
		actualScore += 5;
	}

	@Test
	void createRoad_IllegalEndPoints() throws Exception {
		maxScore += 9;
		try {
			theFacade.createTwoWayRoad("X1", null, antwerp, 300, 2.22F, 1.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
		try {
			Location locationOutOfBounds = theFacade.createLocation(new double[] { -5.0, 40.0 }, "Abv");
			theFacade.createTwoWayRoad("X2", locationOutOfBounds, antwerp, 600, 5.55F, 4.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
		try {
			Location locationOutOfBounds = theFacade.createLocation(new double[] { 71.0, 12.5 }, "X12");
			theFacade.createTwoWayRoad("X3", locationOutOfBounds, antwerp, 600, 5.55F, 4.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void createRoad_IllegalSpeedLimit() throws Exception {
		maxScore += 5;
		try {
			theFacade.createTwoWayRoad("X1", leuven, antwerp, -10, -4.56F, 3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_IllegalAverageSpeed() throws Exception {
		maxScore += 5;
		try {
			theFacade.createTwoWayRoad("X1", leuven, antwerp, -10, 5.0F, -3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void createRoad_SpeedLimitBelowAverageSpeed() throws Exception {
		maxScore += 8;
		try {
			theFacade.createTwoWayRoad("X1", leuven, antwerp, -10, 2.0F, 3.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 8;
		}
	}

	@Test
	void terminateRoad_SingleCase() throws Exception {
		maxScore += 10;
		int nbAdjoiningRoadsLeuven = theFacade.getLocationAllAdjoiningRoads(leuven).size();
		int nbAdjoiningRoadsAntwerp = theFacade.getLocationAllAdjoiningRoads(antwerp).size();
		someRoad = theFacade.createTwoWayRoad("X99", leuven, antwerp, 1500, 16.66F, 12.22F);
		theFacade.terminateRoad(someRoad);
		assertTrue(theFacade.isTerminatedRoad(someRoad));
		assertTrue(!theFacade.locationHasAsAdjoiningRoad(leuven, someRoad));
		assertEquals(nbAdjoiningRoadsLeuven, theFacade.getLocationAllAdjoiningRoads(leuven).size());
		assertTrue(!theFacade.locationHasAsAdjoiningRoad(antwerp, someRoad));
		assertEquals(nbAdjoiningRoadsAntwerp, theFacade.getLocationAllAdjoiningRoads(antwerp).size());
		actualScore += 10;
	}

	@Test
	void changeIdentification_LegalCase() throws Exception {
		maxScore += 3;
		theFacade.changeRoadIdentification(t1_leuven_antwerp, "E12");
		assertEquals("E12", theFacade.getRoadIdentification(t1_leuven_antwerp));
		actualScore += 3;
	}

	@Test
	void changeIdentification_IllegalCases() throws Exception {
		maxScore += 14;
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, null);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "012");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "A");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "Aa");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "A2b");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadIdentification(t1_leuven_antwerp, "E333");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeIdentification_NonUniqueCode() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadIdentification(t2_leuven_brussels, "T1");
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 10;
		}
	}

	@Test
	void getStartEndLocations_TwoWayRoad() throws Exception {
		maxScore += 5;
		assertArrayEquals(theFacade.getEndPoints(t1_leuven_antwerp), theFacade.getStartLocations(t1_leuven_antwerp));
		assertArrayEquals(theFacade.getEndPoints(t1_leuven_antwerp), theFacade.getEndLocations(t1_leuven_antwerp));
		actualScore += 5;
	}

	@Test
	void getStartEndLocations_OneWayRoad() throws Exception {
		maxScore += 5;
		assertArrayEquals(new Location[] { brussels }, theFacade.getStartLocations(O1_brussels_gent_oneWay));
		assertArrayEquals(new Location[] { gent }, theFacade.getEndLocations(O1_brussels_gent_oneWay));
		actualScore += 5;
	}

	@Test
	void getStartEndLocations_AlternatingRoad() throws Exception {
		maxScore += 5;
		assertArrayEquals(new Location[] { gent }, theFacade.getStartLocations(a1_gent_brussels_alter));
		assertArrayEquals(new Location[] { brussels }, theFacade.getEndLocations(a1_gent_brussels_alter));
		actualScore += 5;
	}

	@Test
	void changeLength_LegalCase() throws Exception {
		maxScore += 3;
		int newLength = theFacade.getRoadLength(t1_leuven_antwerp) + 200;
		theFacade.changeRoadLength(t1_leuven_antwerp, newLength);
		assertEquals(newLength, theFacade.getRoadLength(t1_leuven_antwerp));
		actualScore += 3;
	}

	@Test
	void changeLength_IllegalCase() throws Exception {
		maxScore += 5;
		theFacade.changeRoadLength(t1_leuven_antwerp, -1);
		assertTrue(theFacade.getRoadLength(t1_leuven_antwerp) > 0);
		actualScore += 5;
	}

	@Test
	void changeSpeedLimit_LegalCase() throws Exception {
		maxScore += 3;
		float newSpeedLimit = theFacade.getRoadSpeedLimit(t1_leuven_antwerp) + 10.0F;
		theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, newSpeedLimit);
		assertEquals(newSpeedLimit, theFacade.getRoadSpeedLimit(t1_leuven_antwerp), 0.1E-10);
		actualScore += 3;
	}

	@Test
	void changeSpeedLimit_IllegalCases() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, Float.NaN);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, Float.NEGATIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, Float.POSITIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, SPEED_OF_LIGHT + 100.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, -0.5E-10F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeSpeedLimit_ValueBelowAverageSpeed() throws Exception {
		maxScore += 15;
		try {
			float speedLimit = theFacade.getRoadAverageSpeed(t1_leuven_antwerp) - 1.0F;
			theFacade.changeRoadSpeedLimit(t1_leuven_antwerp, speedLimit);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void changeAverageSpeed_LegalCase() throws Exception {
		maxScore += 3;
		float averageSpeed = theFacade.getRoadAverageSpeed(t1_leuven_antwerp) / 2.0F;
		theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, averageSpeed);
		assertEquals(averageSpeed, theFacade.getRoadAverageSpeed(t1_leuven_antwerp), 0.1E-10);
		actualScore += 3;
	}

	@Test
	void changeAverageSpeed_IllegalCases() throws Exception {
		maxScore += 10;
		try {
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, Float.NaN);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, Float.NEGATIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, Float.POSITIVE_INFINITY);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, SPEED_OF_LIGHT + 100.0F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
		try {
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, -0.5E-8F);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void changeAverageSpeed_ValueExceedingSpeedLimit() throws Exception {
		maxScore += 15;
		try {
			float averageSpeed = theFacade.getRoadSpeedLimit(t1_leuven_antwerp) + 2.0F;
			theFacade.changeRoadAverageSpeed(t1_leuven_antwerp, averageSpeed);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void getCurrentDelay_IllegalCaseOneWayRaod() throws Exception {
		maxScore += 3;
		try {
			theFacade.getRoadDelayinDirection(O2_hasselt_gent_oneWay, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void getCurrentDelay_IllegalCaseAlternatingRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.getRoadDelayinDirection(a1_gent_brussels_alter, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void changeCurrentDelay_LegalCases() throws Exception {
		maxScore += 6;
		theFacade.changeRoadDelayinDirection(t1_leuven_antwerp, 12.0F, true);
		assertEquals(12.0F, theFacade.getRoadDelayinDirection(t1_leuven_antwerp, true), 0.1E-10);
		actualScore += 3;
		theFacade.changeRoadDelayinDirection(t1_leuven_antwerp, 15.0F, false);
		assertEquals(15.0F, theFacade.getRoadDelayinDirection(t1_leuven_antwerp, false), 0.1E-10);
		actualScore += 3;
	}

	@Test
	void changeCurrentDelay_IllegalCaseOneWayRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.changeRoadDelayinDirection(O2_hasselt_gent_oneWay, 4.0F, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void changeCurrentDelay_IllegalCaseAlternatingRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.changeRoadDelayinDirection(a1_gent_brussels_alter, 5.0F, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void isBlocked_IllegalCasOneWayRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.getRoadIsBlocked(O2_hasselt_gent_oneWay, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void isBlocked_IllegalCaseAlternatingRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.getRoadIsBlocked(a1_gent_brussels_alter, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void changeIsBlocked_LegalCases() throws Exception {
		maxScore += 8;
		theFacade.changeRoadBlockedState(t1_leuven_antwerp, true, true);
		assertTrue(theFacade.getRoadIsBlocked(t1_leuven_antwerp, true));
		actualScore += 2;
		theFacade.changeRoadBlockedState(t1_leuven_antwerp, false, true);
		assertFalse(theFacade.getRoadIsBlocked(t1_leuven_antwerp, true));
		actualScore += 2;
		theFacade.changeRoadBlockedState(t1_leuven_antwerp, true, false);
		assertTrue(theFacade.getRoadIsBlocked(t1_leuven_antwerp, false));
		actualScore += 2;
		theFacade.changeRoadBlockedState(t1_leuven_antwerp, false, false);
		assertFalse(theFacade.getRoadIsBlocked(t1_leuven_antwerp, false));
		actualScore += 2;
	}

	@Test
	void changeIsBlocked_IllegalCaseOneWayRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.changeRoadBlockedState(O2_hasselt_gent_oneWay, true, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void changeIsBlocked_IllegalCaseAlternatingRod() throws Exception {
		maxScore += 3;
		try {
			theFacade.changeRoadBlockedState(a1_gent_brussels_alter, false, false);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void reverseTraversalDirection_LegalCase() throws Exception {
		maxScore += 10;
		theFacade.reverseTraversalDirection(a1_gent_brussels_alter);
		assertArrayEquals(new Location[] { brussels }, theFacade.getStartLocations(a1_gent_brussels_alter));
		assertArrayEquals(new Location[] { gent }, theFacade.getEndLocations(a1_gent_brussels_alter));
		actualScore += 10;
	}

	@Test
	void reverseTraversalDirection_IllegalCaseOneWayRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.reverseTraversalDirection(O2_hasselt_gent_oneWay);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void reverseTraversalDirection_IllegalTwoWayRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.reverseTraversalDirection(t1_leuven_antwerp);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	/*******************
	 * Tests for ROUTE *
	 *******************/

	@Test
	void createRoute_NonEffectiveStartLocation() throws Exception {
		maxScore += 3;
		try {
			theFacade.createRoute(null);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void createRoute_StartLocationNotInFirstSegment() throws Exception {
		maxScore += 6;
		try {
			theFacade.createRoute(brussels, t1_leuven_antwerp);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 6;
		}
	}

	@Test
	void createRoute_NoSegments() throws Exception {
		maxScore += 6;
		Route theRoute = theFacade.createRoute(leuven);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(0, theFacade.getRouteSegments(theRoute).length);
		actualScore += 6;
	}

	@Test
	void createRoute_SingleSegment() throws Exception {
		maxScore += 9;
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(1, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		actualScore += 9;
	}

	@Test
	void createRoute_ProperSequenceOfRoads() throws Exception {
		maxScore += 12;
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp, t4_brussels_antwerp, O1_brussels_gent_oneWay, t5_leuven_gent);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(4, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(O1_brussels_gent_oneWay, theFacade.getRouteSegments(theRoute)[2]);
		actualScore += 12;
	}

	@Test
	void createRoute_ProperSequenceOfRoadsAndRoutes() throws Exception {
		maxScore += 20;
		Route subroute1 = theFacade.createRoute(antwerp, t4_brussels_antwerp);
		Route subroute2 = theFacade.createRoute(gent, a1_gent_brussels_alter, t4_brussels_antwerp);
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp, subroute1, O1_brussels_gent_oneWay, subroute2);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(4, theFacade.getRouteSegments(theRoute).length);
		assertEquals(subroute1, theFacade.getRouteSegments(theRoute)[1]);
		assertEquals(subroute2, theFacade.getRouteSegments(theRoute)[3]);
		actualScore += 20;
	}

	@Test
	void createRoute_ImProperSequenceOfSegments() throws Exception {
		maxScore += 15;
		try {
			theFacade.createRoute(leuven, t1_leuven_antwerp, t4_brussels_antwerp, O1_brussels_gent_oneWay, t2_leuven_brussels);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void createRoute_OneWayRoadWrongDirection() throws Exception {
		maxScore += 10;
		try {
			theFacade.createRoute(leuven, t5_leuven_gent, O1_brussels_gent_oneWay, t2_leuven_brussels);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 10;
		}
	}

	@Test
	void createRoute_NullSequenceSegments() throws Exception {
		maxScore += 3;
		try {
			theFacade.createRoute(leuven, (Object[])null);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void createRoute_SameRoadSeveralTimes() throws Exception {
		maxScore += 8;
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp, t4_brussels_antwerp, t4_brussels_antwerp, t1_leuven_antwerp);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(4, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[3]);
		actualScore += 8;
	}

	@Test
	void createRoute_CircularRoad() throws Exception {
		maxScore += 8;
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp, c1_circularRoad_antwerp, c1_circularRoad_antwerp, t1_leuven_antwerp);
		assertEquals(leuven, theFacade.getRouteStartLocation(theRoute));
		assertEquals(4, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[3]);
		actualScore += 8;
	}

	@Test
	void createRoute_LeakTest() throws Exception {
		maxScore += 8;
		Road[] roads = { t1_leuven_antwerp, c1_circularRoad_antwerp };
		Route theRoute = theFacade.createRoute(leuven, (Object[])roads);
		roads[1] = null;
		assertEquals(2, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(c1_circularRoad_antwerp, theFacade.getRouteSegments(theRoute)[1]);
		actualScore += 8;
	}

	@Test
	void getAllSegments_LeakTest() throws Exception {
		maxScore += 8;
		Route theRoute = theFacade.createRoute(leuven, t1_leuven_antwerp, c1_circularRoad_antwerp);
		Object[] allSegments = theFacade.getRouteSegments(theRoute);
		allSegments[0] = null;
		assertEquals(2, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(c1_circularRoad_antwerp, theFacade.getRouteSegments(theRoute)[1]);
		actualScore += 8;
	}

	@Test
	void addSegment_LegalCase() throws Exception {
		maxScore += 6;
		theFacade.addRouteSegment(emptyRoute_leuven, t1_leuven_antwerp);
		theFacade.addRouteSegment(emptyRoute_leuven, t4_brussels_antwerp);
		theFacade.addRouteSegment(emptyRoute_leuven, O1_brussels_gent_oneWay);
		assertEquals(leuven, theFacade.getRouteStartLocation(emptyRoute_leuven));
		assertEquals(3, theFacade.getRouteSegments(emptyRoute_leuven).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(emptyRoute_leuven)[0]);
		assertEquals(O1_brussels_gent_oneWay, theFacade.getRouteSegments(emptyRoute_leuven)[2]);
		actualScore += 6;
	}

	@Test
	void addSegment_NullRoad() throws Exception {
		maxScore += 3;
		try {
			theFacade.addRouteSegment(emptyRoute_leuven, null);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 3;
		}
	}

	@Test
	void addSegment_NonMatchingRoads() throws Exception {
		maxScore += 20;
		try {
			theFacade.addRouteSegment(route_leuven_antwerp_brussels_gent, t2_leuven_brussels);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
		try {
			theFacade.addRouteSegment(route_leuven_antwerp_brussels_gent, O2_hasselt_gent_oneWay);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
		try {
			theFacade.reverseTraversalDirection(a1_gent_brussels_alter);
			theFacade.addRouteSegment(route_leuven_antwerp_brussels_gent, a1_gent_brussels_alter);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
		try {
			theFacade.addRouteSegment(route_leuven_antwerp_brussels_gent, circularRoute_antwerp_leuven_brussels_brussels_antwerp);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 5;
		}
	}

	@Test
	void addSegment_DirectCycle() throws Exception {
		maxScore += 15;
		try {
			theFacade.addRouteSegment(circularRoute_antwerp_leuven_brussels_brussels_antwerp, circularRoute_antwerp_leuven_brussels_brussels_antwerp);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 15;
		}
	}

	@Test
	void addSegment_IndirectCycle() throws Exception {
		maxScore += 30;
		Route theEnclosingRoute = theFacade.createRoute(antwerp, circularRoute_antwerp_leuven_brussels_brussels_antwerp, c1_circularRoad_antwerp);
		try {
			theFacade.addRouteSegment(circularRoute_antwerp_leuven_brussels_brussels_antwerp, theEnclosingRoute);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 30;
		}
	}

	@Test
	void removeSegment_LegalCase() throws Exception {
		maxScore += 25;
		Route theRoute = circularRoute_antwerp_leuven_brussels_brussels_antwerp;
		theFacade.removeRouteSegment(theRoute, 2);
		assertEquals(3, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(t2_leuven_brussels, theFacade.getRouteSegments(theRoute)[1]);
		assertEquals(t4_brussels_antwerp, theFacade.getRouteSegments(theRoute)[2]);
		actualScore += 10;
		theFacade.removeRouteSegment(theRoute, 2);
		assertEquals(2, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		assertEquals(t2_leuven_brussels, theFacade.getRouteSegments(theRoute)[1]);
		actualScore += 5;
		theFacade.removeRouteSegment(theRoute, 1);
		assertEquals(1, theFacade.getRouteSegments(theRoute).length);
		assertEquals(t1_leuven_antwerp, theFacade.getRouteSegments(theRoute)[0]);
		actualScore += 5;
		theFacade.removeRouteSegment(theRoute, 0);
		assertEquals(0, theFacade.getRouteSegments(theRoute).length);
		actualScore += 5;
	}

	@Test
	void removeSegment_IndexOutOfRange() throws Exception {
		maxScore += 2;
		try {
			theFacade.removeRouteSegment(route_leuven_antwerp_brussels_gent, 3);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 2;
		}
	}

	@Test
	void removeSegment_ImproperSegment() throws Exception {
		maxScore += 8;
		try {
			theFacade.removeRouteSegment(route_leuven_antwerp_brussels_gent, 1);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 8;
		}
	}

	@Test
	void removeSegment_FirstSegmentNonMatchingStartLocation() throws Exception {
		maxScore += 8;
		try {
			theFacade.removeRouteSegment(route_leuven_antwerp_brussels_gent, 0);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 8;
		}
	}

	@Test
	void getTotalLength_SingleCase() throws Exception {
		maxScore += 6;
		theFacade.changeRoadLength(t4_brussels_antwerp, 55);
		theFacade.changeRoadLength(c2_circularRoad_brussels, 40);
		theFacade.changeRoadLength(O1_brussels_gent_oneWay, 70);
		Route theRoute = theFacade.createRoute(antwerp, t4_brussels_antwerp, c2_circularRoad_brussels, O1_brussels_gent_oneWay);
		assertEquals(55 + 40 + 70, theFacade.getRouteTotalLength(theRoute));
		actualScore += 6;
	}

	@Test
	void isTraversable_TrueCase() throws Exception {
		maxScore += 8;
		theFacade.changeRoadBlockedState(t4_brussels_antwerp, true, true);
		theFacade.reverseTraversalDirection(a1_gent_brussels_alter);
		Route theRoute = theFacade.createRoute(antwerp, t4_brussels_antwerp, c2_circularRoad_brussels, a1_gent_brussels_alter);
		assertTrue(theFacade.isRouteTraversable(theRoute));
		actualScore += 8;
	}

	@Test
	void isTraversable_FalseCase() throws Exception {
		maxScore += 8;
		theFacade.changeRoadBlockedState(t4_brussels_antwerp, true, false);
		Route theRoute = theFacade.createRoute(antwerp, t4_brussels_antwerp, c2_circularRoad_brussels, O1_brussels_gent_oneWay);
		assertFalse(theFacade.isRouteTraversable(theRoute));
		actualScore += 8;
	}

	@Test
	void getAllLocations_EmptyRoute() throws Exception {
		maxScore += 4;
		assertArrayEquals(new Location[] { leuven }, theFacade.getAllLocations(emptyRoute_leuven));
		actualScore += 4;
	}

	@Test
	void getAllLocations_SingleRoadRoute() throws Exception {
		maxScore += 4;
		Route theRoute = theFacade.createRoute(brussels, t4_brussels_antwerp);
		assertArrayEquals(new Location[] { brussels, antwerp }, theFacade.getAllLocations(theRoute));
		theRoute = theFacade.createRoute(brussels, c2_circularRoad_brussels);
		assertArrayEquals(new Location[] { brussels, brussels }, theFacade.getAllLocations(theRoute));
		actualScore += 4;
	}

	@Test
	void getAllLocations_MultipleRoadRoute() throws Exception {
		maxScore += 10;
		assertArrayEquals(new Location[] { leuven, antwerp, brussels, gent },
				theFacade.getAllLocations(route_leuven_antwerp_brussels_gent));
		assertArrayEquals(new Location[] { antwerp, leuven, brussels, brussels, antwerp },
				theFacade.getAllLocations(circularRoute_antwerp_leuven_brussels_brussels_antwerp));
		actualScore += 10;
	}

	@Test
	void getAllLocations_NestedRoute() throws Exception {
		maxScore += 15;
		Route theEnclosingRoute = theFacade.createRoute
				(antwerp, circularRoute_antwerp_leuven_brussels_brussels_antwerp, t1_leuven_antwerp);
		assertArrayEquals(new Location[] { antwerp, leuven, brussels, brussels, antwerp, leuven },
				theFacade.getAllLocations(theEnclosingRoute));
		actualScore += 15;
	}
	
	@Test
	void addSegment_InvalidatingEnclosingRoute() throws Exception {
		maxScore += 25;
		Route theEnclosingRoute = theFacade.createRoute
				(antwerp, circularRoute_antwerp_leuven_brussels_brussels_antwerp, t1_leuven_antwerp);
		theFacade.addRouteSegment(circularRoute_antwerp_leuven_brussels_brussels_antwerp, t4_brussels_antwerp);
		try {
			// The composition of the enclosing route is no longer valid because the
			// end of the first segment (changed to Brussels because of the added segment)
			// does not match with the end points of the road from Leuven to Antwerp.
			theFacade.isRouteTraversable(theEnclosingRoute);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 25;
		}
	}
	
	@Test
	void removeSegment_InvalidatingEnclosingRoute() throws Exception {
		maxScore += 25;
		Route theEnclosingRoute = theFacade.createRoute
				(antwerp, circularRoute_antwerp_leuven_brussels_brussels_antwerp, t1_leuven_antwerp);
		theFacade.removeRouteSegment(circularRoute_antwerp_leuven_brussels_brussels_antwerp, 3);
		try {
			// The composition of the enclosing route is no longer valid because the
			// end of the first segment (changed to Brussels because of the removed segment)
			// does not match with the end points of the road from Leuven to Antwerp.
			theFacade.isRouteTraversable(theEnclosingRoute);
			fail("Expecting exception to be thrown");
		} catch (ModelException exc) {
			actualScore += 25;
		}
	}

}
