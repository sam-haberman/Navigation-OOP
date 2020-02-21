package connections;


/**
 * A class of roads where each road has an ID, connects two end points, has a length in meters,
 * a speed limit an average road speed under normal conditions in meters per second,
 * a delay in seconds, and a blocked status for each endpoint direction. This road can alternate its start and end location which
 * is the direction that the road is being traveled.
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
 * @invar The delay in the direction of an end point must be a valid delay for any road, but throws an error if the direction
 *        being queried is not the current direction of the end of the road.
 * 		| isValidDelay(getDelayDirectionEndPoint1())
 * 		| isValidDelay(getDelayDirectionEndPoint2())
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */

public class AlternatingRoad extends Road{
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
     * @post The given location1 is stored as location1.
     * | new.location1 = location1
     * @post The given location2 is stored as location2.
     * | new.location2 = location2
     */
    public AlternatingRoad(String id, Location location1, Location location2, int length, float roadSpeed) {
        super(id, location1, location2, length, roadSpeed);
        startLocation = location1;
        endLocation = location2;
        super.location1 = location1;
        super.location2 = location2;
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
     * @post The given location1 is stored as location1.
     * | new.location1 = location1
     * @post The given location2 is stored as location2.
     * | new.location2 = location2
     */
    public AlternatingRoad(String id, Location location1, Location location2, int length, float speedlimit, float roadSpeed) {
        super(id, location1, location2, length, speedlimit, roadSpeed);
        startLocation = location1;
        endLocation = location2;
        super.location1 = location1;
        super.location2 = location2;
    }

    /**
     * If the road is traveling in the direction of endpoint two does nothing, else runs the method from Road.
     * @param delay The new delay time for the road going towards endpoint one
     *
     * @post The delay in the direction of endpoint one is equal to the given delay, if travelling in the direction of endpoint one
     * | if !(directionOfRoad)
     * |    then new.getDelayDirectionOne() == delay
     * @throws NullPointerException If travelling in direction of endpoint two.
     */
    @Override
    public void setDelayDirectionEndPointOne(float delay) throws NullPointerException {
        if (!directionOfRoad) {
            super.setDelayDirectionEndPointOne(delay);
        }
        else
        	throw new NullPointerException();
    }

    /**
     * If the road is traveling in the direction of endpoint one does nothing, else runs the method from Road.
     * @param delay The new delay time for the road going towards endpoint one
     *
     * @post The delay in the direction of endpoint two is equal to the given delay, if travelling in the direction of endpoint two.
     * | if (directionOfRoad)
     * |    then new.getDelayDirectionTwo() == delay
     * @throws NullPointerException If not travelling in direction of endpoint two.
     */
    @Override
    public void setDelayDirectionEndPointTwo(float delay) throws NullPointerException {
        if (directionOfRoad) {
            super.setDelayDirectionEndPointTwo(delay);
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Gets the delay of endpoint one if travelling in direction of endpoint one.
     * @throws NullPointerException If travelling in direction of endpoint two.
     */
    @Override
    public float getDelayDirectionEndPointOne() throws NullPointerException{
        if (!directionOfRoad){
            return super.getDelayDirectionEndPointOne();
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Gets the delay of endpoint two if travelling in the direction of endpoint two.
     * @throws NullPointerException If travelling in direction of endpoint one.
     */
    @Override
    public float getDelayDirectionEndPointTwo() throws NullPointerException{
        if (directionOfRoad){
            return super.getDelayDirectionEndPointTwo();
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Sets the blocked status of the road to the given value if the road is travelling in the direction of endpoint one.
     * @param blocked The new blocked status of the road.
     * @post The blocked status of the road is equal to the given blocked boolean if the road is travelling in the direction of endpoint one.
     *  | if (!directionOfRoad)
     *  |   new.isBlockedDirectionEndPointOne == blocked
     * @throws NullPointerException If road is travelling in direction of endpoint two
     */
    @Override
    public void setBlockedDirectionEndPointOne(boolean blocked) throws NullPointerException{
        if (!directionOfRoad){
            super.setBlockedDirectionEndPointOne(blocked);
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Sets the blocked status of the road to the given value if the road is travelling in the direction of endpoint tow.
     * @param blocked The new blocked status of the road.
     * @post The blocked status of the road is equal to the given blocked boolean if the road is travelling in the direction of endpoint two.
     * |    if (directionOfRoad)
     *          new.isBlockedDirectionEndPointTwo == blocked
     * @throws NullPointerException If road is not travelling in direction of endpoint two
     */
    @Override
    public void setBlockedDirectionEndPointTwo(boolean blocked) throws NullPointerException{
        if (directionOfRoad){
            super.setBlockedDirectionEndPointTwo(blocked);
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Returns the blocked status of the road, only works if road is going in direction of endpoint one
     * @throws NullPointerException If road is travelling in direction of endpoint two
     */
    @Override
    public boolean isBlockedDirectionEndPointOne() throws NullPointerException{
        if (!directionOfRoad){
            return super.isBlockedDirectionEndPointOne();
        }
        else
        	throw new NullPointerException();
    }

    /**
     * Returns the blocked status of the road, only works if road is going in direction of endpoint two.
     * @throws NullPointerException If road is travelling in direction of endpoint one.
     */
    @Override
    public boolean isBlockedDirectionEndPointTwo() throws NullPointerException {
      if (directionOfRoad){
      	return super.isBlockedDirectionEndPointTwo();
      }
      else
    	  throw new NullPointerException();
  	}


    /**
	 * Get all valid start locations for this road
	 * For an alternating road, this returns the start location, which is updated by changing the direction
	 */
	@Override
	public Location[] getStartLocations() {
		Location[] array = new Location[] {startLocation};
		return array;
	}

	/**
	 * Get all valid end locations for this road
 	 * For an alternating road, this returns the end location, which is updated by changing the direction
	 */
	@Override
	public Location[] getEndLocations() {
		Location[] array = new Location[] {endLocation};
		return array;
	}

    /**
     * Returns the other location for the road when given a location.
     * @param location The location given
     * @return If given the start location returns the end location and vice versa.
     * |    if location == startLocation
     * |        return endLocation
     * |    else
     * |        return startLocation
     */
	@Override
	public Location getOtherLocation(Location location) {
		if(location==startLocation)
			return endLocation;
		return startLocation;
	}

    /**
     * Does nothing, no Route Segments for a Road.
     */
	@Override
	public Segments[] getRouteSegments() {
		return null;
	}

    /**
     * Method to check whether a Object contains itself.
     * @param segment The segment to check
     * @return False always because roads do not have segments and cannot contain themselves.
     */
	@Override
	public boolean containsItself(Object segment) {
		return false;
	}   
	
	/**
	 * This method overrides the toString representation of an instance of the alternating road class
	 *
	 * @return Returns a string stating "This alternating road has the following properties" which lists the ID, both endpoints,
	 * the length of the road in meters, the speed limit and the average speed of the road in meters per second,
	 * the blocked status of the road and the delay in seconds.
	 */
	@Override
	public String toString() {
		return ("This alternating road has the following properties:" + '\n' + "ID: " + this.getID() + '\n' +
				"End point 1: " + this.getEndPoint1().getAddress() + '\n' + "End point 2: " + this.getEndPoint2().getAddress() + '\n' + "Length: " + this.getLength()
				+ '\n' + "Speed limit: " + this.getSpeedlimit() + '\n' + "Average speed: " + this.getRoadSpeed() + '\n' + "Blocked in the direction of "+this.getEndPoint1().getAddress()+": " + this.isBlockedDirectionEndPointOne() + '\n' +
				"Blocked in the direction of "+this.getEndPoint2().getAddress() + ": " + this.isBlockedDirectionEndPointTwo() + '\n' + "Delay in the direction of "+this.getEndPoint1().getAddress()+": " + this.getDelayDirectionEndPointOne() + '\n' +
				"Delay in the direction of "+this.getEndPoint2().getAddress()+ ": " + this.getDelayDirectionEndPointTwo() + '\n');
	}

}

