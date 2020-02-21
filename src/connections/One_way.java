package connections;

/**
 * A class of roads where each road has an ID, connects two end points, has a length in meters,
 * a speed limit an average road speed under normal conditions in meters per second,
 * a delay in seconds, and a blocked status for each endpoint direction. Each road only travels in the direction of its
 * second location.
 *
 * @invar The ID of each road must be a valid ID for any road
 * 		| isValidID(getID())
 * @invar The end point of each road must be a valid end point for any road
 * 		| isValidEndPoint(location1.getCoordinate())
 * 		| isValidEndPoint(location2.getCoordinate())
 * @invar The length of each road must be a valid length for any road
 * 		| isValidLength(getLength())
 * @invar The speed limit of each road must be a valid speed limit for any road
 * 		| isValidSpeedLimit(getSpeedLimit())
 * @invar The average road speed of each road must be a valid average road speed for any road
 * 		| isValidRoadSpeed(getRoadSpeed())
 * @invar The delay in the direction of the second end point must be a valid delay for any road
 * 		| isValidDelay(getDelayDirectionEndPoint2())
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */

public class One_way extends Road{
    /**
     * Initializes a new non-terminated one-way road with given ID, first and second location and
     * roadspeed. The road will be given the standard speed limit of 19.5 m/s.
     *
     * @param id        The unique identifier for our new road.
     * @param location1 The first endpoint of the new road.
     * @param location2 The second endpoint of the new road.
     * @param length    The length of the road.
     * @param roadSpeed The average speed obtained on the new road under standard conditions.
     * @pre The given end point must be a valid end point for a road
     * | isValidEndPoint(location1.getCoordinate())
     * @pre The given end point must be a valid end point for a road
     * | isValidEndPoint(location2.getCoordinate())
     * @post The ID of this new road will be equal to the given id
     * | new.getID() == id
     * @post The first endpoint of the new road will be equal to the values of the given endPoint1
     * | new.getEndPoint1() == endPoint1
     * @post The second endpoint of the new road will be equal to the values of the given endPoint2
     * | new.getEndPoint2() == endPoint2
     * @post The length of the new road will be equal to the given length in meters
     * | new.getLength() == length
     * @post The average roadspeed for the new road will be equal to the given roadSpeed
     * | new.getRoadSpeed() == roadSpeed
     * @post This road is added to the list of adjoining roads for location 1.
     * | new.location1.hasAsAdjoiningRoad(this) == true
     * @post This road is added to the list of adjoining roads for location 2.
     * | new.location2.hasAsAdjoiningRoad(this) == true
     * @post The start location of this road is equal to location1.
     * | new.startLocation == location1
     * @post The end location of this road is equal to location2.
     * | new.endLocation == location2
     */
    public One_way(String id, Location location1, Location location2, int length, float roadSpeed) {
        super(id, location1, location2, length, roadSpeed);
        startLocation = location1;
        endLocation = location2;
    }

    /**
     * Initialize a new non-terminated one-way road with given ID, first and second location, speed limit and roadspeed.
     *
     * @param id         The unique identifier for our new road.
     * @param location1  The first endpoint of the new road.
     * @param location2  The second endpoint of the new road.
     * @param length     The length of the new road.
     * @param speedlimit The speed limit of the new road.
     * @param roadSpeed  The average speed obtained on the new road under standard conditions.
     * @pre The given first endpoint must be valid endpoint for a road
     * | isValidEndPoint(location1.getCoordinate())
     * @pre The given second endpoint must be valid endpoint for a road
     * | isValidEndPoint(location2.getCoordinate())
     * @post The ID of this new road will be equal to the given id
     * | new.getID() == id
     * @post The first endpoint of the new road will be equal to the values of the given endPoint1
     * | new.getEndPoint1() == endPoint1
     * @post The second endpoint of the new road will be equal to the values of the given endPoint2
     * | new.getEndPoint2() == endPoint2
     * @post The length of the new road will be equal to the given length in meters
     * | new.getLength() == length
     * @post The speed limit of the new road will be equal to the given speedlimit
     * | new.getSpeedLimit() == speedlimit
     * @post The average roadspeed for the new road will be equal to the given roadSpeed
     * | new.getRoadSpeed() == roadSpeed
     * @post This road is added to the list of adjoining roads for location 1.
     * | new.location1.hasAsAdjoiningRoad(this) == true
     * @post This road is added to the list of adjoining roads for location 2.
     * | new.location2.hasAsAdjoiningRoad(this) == true
     * @post The start location of this road is equal to location1.
     * | new.startLocation == location1
     * @post The end location of this road is equal to location2.
     * | new.endLocation == location2
     */
    public One_way(String id, Location location1, Location location2, int length, float speedlimit, float roadSpeed) {
        super(id, location1, location2, length, speedlimit, roadSpeed);
        startLocation = location1;
        endLocation = location2;
    }

    /**
     * Does nothing, road only travels in direction of endpoint two, the end location.
     * @param delay The new delay time for the road going towards endpoint one
     * @throws NullPointerException Every time method is called
     */
    @Override
    public void setDelayDirectionEndPointOne(float delay) throws NullPointerException{
    	throw new NullPointerException();
    }

    /**
     * Road never travels in the direction of endpoint one.
     * @throws NullPointerException Every time method is called.
     */
    @Override
    public float getDelayDirectionEndPointOne() throws NullPointerException{
        throw new NullPointerException();
    }

    /**
     * Does nothing, road only travels in direction of endpoint two, the end location.
     * @param blocked The new blocked status of the road.
     * @throws NullPointerException Every time method is called
     */
    @Override
    public void setBlockedDirectionEndPointOne(boolean blocked) throws NullPointerException{
    	throw new NullPointerException();
    }

    /**
     * Road never travels in direction of endpoint one
     * @throws NullPointerException Every time method is called
     */
    @Override
    public boolean isBlockedDirectionEndPointOne() throws NullPointerException{
        throw new NullPointerException();
    }

    /**
     * Get all valid start locations for this road
     * For a one way road, this is the startLocation
     */
	@Override
	public Location[] getStartLocations() {
		// TODO Auto-generated method stub
		Location[] array = new Location[] {startLocation};
		return array;
	}

	/**
	 * Get all valid end locations for this road
	 * For a one way road, this is the endLocation
	 */
	@Override
	public Location[] getEndLocations() {
		// TODO Auto-generated method stub
		Location[] array = new Location[] {endLocation};
		return array;
	}

    /**
     * Gets the other location of the road when given a location.
     * @param location The location to get the other end of.
     * @return Always returns endLocation since this is a one way road with the same never changing end location.
     *  |       return this.endLocation
     */
	@Override
	public Location getOtherLocation(Location location) {
		return this.endLocation;
		
	}

    /**
     * Gets the route segments.
     * @return Null because a Road does not have road segments.
     */
	@Override
	public Segments[] getRouteSegments() {
		return null;
	}

    /**
     * Checks to see whether an Object contains itself.
     * @param segment The segment to check
     * @return False because a Road does not have segments and cannot contain itself.
     */
	@Override
	public boolean containsItself(Object segment) {
		return false;
	}
	
	/**
	 * This method overrides the toString representation of an instance of the one way road class
	 *
	 * @return Returns a string stating "This one way has the following properties" which lists the ID, both endpoints,
	 * the length of the road in meters, the speed limit and the average speed of the road in meters per second,
	 * the blocked status of the road and the delay in seconds.
	 */
	@Override
	public String toString() {
		return ("This one-way road has the following properties:" + '\n' + "ID: " + this.getID() + '\n' +
				"End point 1: " + this.getEndPoint1().getAddress() + '\n' + "End point 2: " + this.getEndPoint2().getAddress() + '\n' + "Length: " + this.getLength()
				+ '\n' + "Speed limit: " + this.getSpeedlimit() + '\n' + "Average speed: " + this.getRoadSpeed() + '\n' + 
				"Blocked in the direction of "+this.getEndPoint2().getAddress() + ": " + this.isBlockedDirectionEndPointTwo() + '\n' +
				"Delay in the direction of "+this.getEndPoint2().getAddress()+ ": " + this.getDelayDirectionEndPointTwo() + '\n');
	}

}
