package connections;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *  A class of Routes where every route has a start location and a list of 0 to n connecting road segments in it.
 *
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */
public class Route extends Segments{
	
	/**
	 * Variable registering the set of roadSegments of the route
	 */
	private Object[] roadSegments;
	
	/**
	 * Initialize a new Route with given start location and collection of road segments.
	 *
	 * @param startLocation
	 * 		The start location of this route
	 * @param segments
	 * 		The connecting segments that are a part of this route
	 * @throws IllegalArgumentException
	 * 		The given road segments are not valid connecting segments.
	 * 	|	!areValidSegments(roads)
	 * @throws NullPointerException
	 * 		The given start location is null
	 * 	|	startLocation == null
	 * @post The start location of this route is equal to the given start location
	 * 	|	new.getStartLocation() == startLocation
	 * @post The road segments of this route are equal to the given road segments.
	 * 	|	new.getRouteSegments() == roads
	 */
	public Route(Location startLocation, Object... segments) throws IllegalArgumentException, NullPointerException {
		if (startLocation == null)
			throw new NullPointerException();
		this.startLocation = startLocation;
		if (!areValidSegments(segments))
			throw new IllegalArgumentException();
		roadSegments = segments.clone();
	}

	/**
	 * Checks whether the Route contains itself in one of its segments or nested segments.
	 * @param segment The segment of the Route to check
	 * @return True if the given segment is equal to the Route
	 * |	if(segment ==this)
	 * |		return true
	 * @return True if a sub segment of the route is a subroute that contains the original Route
	 * |		for each object in this.getRouteSegments()
	 * |			if(((Segments) object).containsItself(segment))
	 * |				return True
	 */
	@Override
	public boolean containsItself(Object segment) {
		if(segment==this)
			return true;
		for(Object array1: this.getRouteSegments()) {
				if(((Segments) array1).containsItself(segment))
					return true;
			}				
		return false;
	}


	/**
	 * Checks to see if the given road segments are valid, as well as
	 * recording the last location visited as end location for the route.
	 * @param segments The road segments to check
	 * @return True if each road segment in the given segments connects in a unbroken line where the location that is not the
	 * 			start location for the first road segment is the same as a location in the next segment where in turn the second
	 * 			segments other location is in turn the same as one of the locations in the third road segment until there is a final
	 * 			location in the last road segment where the road ends.
	 * 		|	result == True if
	 * 		|	for 0..segments.length-1
	 *		|		if(((Segments) segments[i]).getStartLocations().length==1){
	 *		|			assert (((Segments) segments[i]).getStartLocations()[0] == startLocation || ((Segments) segments[i]).getEndLocations()[0] == startLocation);}
	 *		|		else {
	 *		|			assert (((Segments) segments[i]).getStartLocations()[0] == startLocation || ((Segments) segments[i]).getEndLocations()[1] == startLocation);}
	 *		|		startLocation = ((Segments) segments[i]).getOtherLocation(startLocation);
	 * @return True if there are zero road segments given
	 * 		|	if segments.length ==0
	 * 		|	result == True
	 * @return True if there is one road segment, and one of the segments locations is the start location of the route.
	 * 		|	if segments.length ==1
	 * 		|		result == Arrays.asList(((Segments) segments[0]).getStartLocations()).contains(startLocation)
	 * @post The final location visited for each Route is the end location of the route.
	 * 	| 	 if segments.length == 0 then
	 * 	|		new.getEndLocation() == startLocation
	 * 	|	 else if roads.length ==1 then
	 * 	|	 	new.getEndLocation() == ((Segments) segments[0]).getOtherLocation(startLocation)
	 * 	|	 else
	 * 	|	 	if segments[-1].getEndPoint1() == segments[-2].getEndPoint2() || segments[-1].getEndPoint1() == segments[-2].getEndPoint2()
	 * 	|	 		then new.getEndLocation() == segments[-1].getEndPoint2()
	 * 	|	 	else
	 *  |	 		new.getEndLocation() == segments[-1].getEndPoint1()
	 */
	public boolean areValidSegments(Object... segments) {
		Location startLocation = this.startLocation;
		if (segments.length == 0) {
			this.endLocation=startLocation;
			return true;
		}
		if(Arrays.asList(((Segments) segments[0]).getStartLocations()).contains(startLocation)){
			if(segments.length == 1) {
				this.endLocation=((Segments) segments[0]).getOtherLocation(startLocation);
				return true;
			}
		startLocation = ((Segments) segments[0]).getOtherLocation(startLocation);
		for (int i = 1; i <= segments.length - 1; i++) {
			if(((Segments) segments[i]).getStartLocations().length==1){
				assert (((Segments) segments[i]).getStartLocations()[0] == startLocation || ((Segments) segments[i]).getEndLocations()[0] == startLocation);
			}
			else {
				assert (((Segments) segments[i]).getStartLocations()[0] == startLocation || ((Segments) segments[i]).getEndLocations()[1] == startLocation);
			}
			startLocation = ((Segments) segments[i]).getOtherLocation(startLocation);
		}
		this.endLocation=startLocation;
		return true;
	}
	return false;	
	}

	/**
	 * Given a location, gets the other location of that road
	 * @param location
	 * 		One of the locations of the road
	 * @return If the first endpoint of the road is the start location returns the second endpoint.
	 * 		|	if (road.getEndPoint1() == startLocation)
	 * 		|	return road.getEndPoint2()
	 * @return If the second endpoint of the road is the start location, returns the first endpoint.
	 * 		|	if (road.getEndPoint2() == startLocation)
	 * 		|	return road.getEndPoint1()
	 */
	
	@Override
	public Location getOtherLocation(Location location) {
		if(location==startLocation)
			return endLocation;
		return startLocation;
	}

	/**
	 * Returns the start location of the Route
	 */
	@Override
	public Location[] getStartLocations() {
		Location[] array = new Location[] {startLocation};
		return array;
	}

	/**
	 * Returns the end location of the Route
	 */
	@Override
	public Location[] getEndLocations() {
		Location[] array = new Location[] {endLocation};
		return array;
	}
	/**
	 * Returns an array of Roads consisting of each road segment in the Route
	 */
	@Override
	public Object[] getRouteSegments() {
		return roadSegments.clone();
	}

	/**
	 * Adds a road segment to the route if it connects to a previous road segment already in the list.
	 *
	 * @param segment The road segment to add
	 * @throws IllegalArgumentException
	 * 		The given road is null
	 * 	|	road == null
	 * @post The given road segment is added to the routes list of road segments, assuming it is a valid list after addition
	 * 	|	if areValidSegments(roadSegments)
	 * 	|		new.roadSegments.contains(road)\
	 * @post The new updated Route does not contain itself(is acyclic)
	 * |	for each segment in the new updated Route
	 * |		assert(!containsItself(segment))
	 */
	public void addRouteSegment(Object segment) throws IllegalArgumentException {
		if (segment == null)
			throw new IllegalArgumentException();
		ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(roadSegments));
		list.add(segment);
		roadSegments = new Road[list.size()];
		list.toArray(roadSegments);
		assert (areValidSegments(roadSegments));
		for(Object item: roadSegments) {
			assert(!containsItself(item));
		}
	}

	/**
	 * Remove the segment at the given index from the sequence of road segments for this route.
	 *
	 * @param  index
	 *         The index of the segment to be removed.
	 * @post   The number of segments of this route is
	 *         decreased by 1.
	 *       | new.getRouteSegments().length = getRouteSegments().length - 1
	 * @post   The total length of this route is decreased with the length of the segment removed
	 * 		| new.getTotalLength() == getTotalLength() - roadSegment[index].getLength()
	 * @post   This route no longer has the segment at the given index as
	 *         one of its segments.
	 *       | ! new.getRouteSegments().contains(roadSegment[index])
	 * @post   All segments registered at an index beyond the index at
	 *         which the removed segment was registered, are shifted
	 *         one position to the left.
	 *       | for each I,J in 0..getTotalLength():
	 *       |   if ( (roadSegments[I] == roadSegments[index] and (I < J) )
	 *       |     then new.roadSegments[J-1] == roadSegments[J]
	 * @post The new array of road segments for the route is a valid list of road segments
	 * 		 | areValidSegments(roadSegments)
	 * @throws IndexOutOfBoundsException
	 *         If the chosen index number is greater than or equal to the number of road segments
	 *         in the route or less than 0
	 *       | (index < 0) || (index >= roadSegments.length)
	 *
	 */
	public void removeRouteSegment(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= roadSegments.length)
			throw new IndexOutOfBoundsException();
		ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(roadSegments));
		list.remove(index);
		roadSegments = new Road[list.size()];
		list.toArray(roadSegments);
		assert (areValidSegments(roadSegments));
	}

	/**
	 * Returns the total length of the road segments in this route.
	 *
	 * @return the sum of lengths of all roads and subroutes that are a part of this Route.
	 *	| int length = 0
	 *	| for road in roadSegments
	 *	|	if(road instanceof Road)
	 *	|		length += road.getLength
	 *	|	if(road instanceof Route
	 *	| 		length += road.getTotalLength()
	 *	| return length
	 */
	@Override
	public int getLength() {
		int length = 0;
		for (Object road : roadSegments) {
			length += ((Segments) road).getLength();
		}
		return length;
	}

	/**
	 * Checks to see if a route is traversable.
	 *
	 * @return Status of route checking whether each road segment in the route is not blocked in the
	 * direction being traveled.
	 * 	|	result == True if
	 * 			for 0..roadSegments.length-1
	 * 				if (locationList.get(i) == roadSegments[i].getEndPoint1()
	 * 					! roadSegments[i].isBlockedDirectionEndPointTwo()
	 * 				if (locationList.get(i) == roadSegments[i].getEndPoint2()
	 * 					! roadSegments[i].isBlockedDirectionEndPointOne()
	 */
	public boolean isTraversable() {
		for(int i=0;i<=roadSegments.length-1;i++) {
			if(getAllLocations()[i]==((Road) roadSegments[i]).getEndPoint1()) {
				if(((Road) roadSegments[i]).isBlockedDirectionEndPointTwo())
					return false;
			}
			if(getAllLocations()[i]==((Road) roadSegments[i]).getEndPoint2()) {
				if(((Road) roadSegments[i]).isBlockedDirectionEndPointOne())
					return false;
			}	
		}
		return true;
	}
	
	/**
	 * Returns an array of all the locations that are visited when traveling through this route
	 *
	 * @return An array consisting of just the start location of the route if the route has no segments.
	 * 	|	if (roadSegments.length ==0)
	 * 	|		new.getAllLocations() == [startLocation]
	 *
	 * @return An array consisting of the start and end location of the given route if it has only one segment whether
	 * 			it is a road or a route
	 * 	|	if (roadSegments.length ==1)
	 * 	|		if (roadSegments[0].getClass() == Route.class)
	 * 	|			return ((Route) roadSegments[0].getAllLocations()
	 * 	|		else
	 * 	|			new.getAllLocations() == [startLocation,endLocation]
	 *
	 * @return An array consisting of each location visited in order during the route if the route has more than 1 segment
	 * |	ArrayList<Object> list = new ArrayList<Object>();
	 * |	Location tracker = this.startLocation;
	 * |	list.add(tracker);
	 * |	for each segment in roadSegments.length -1
	 * |		if (roadSegments[i].getClass() == Route.class)
	 * |			ArrayList<Location> sublist = new ArrayList<Location>()
	 * |			sublist.addAll(Arrays.asList(((Route) roadSegments[0]).getAllLocations()))
	 * |			sublist.remove(0)
	 * |			list.addAll(sublist)
	 * |        else
	 * |			tracker = ((Segments) roadSegments[i]).getOtherLocation(tracker)
	 * |			list.add(tracker)
	 * |	Location[] array = new Location[list.size()];
	 * |	list.toArray(array);
	 * |	return array;
	 */
	public Location[] getAllLocations() {
		ArrayList<Object> list = new ArrayList<Object>();
		Location tracker = this.startLocation;
		list.add(tracker);
		if (roadSegments.length == 0) {
			
		} else if (roadSegments.length == 1) {
			if (roadSegments[0].getClass() == Route.class) {
				return ((Route) roadSegments[0]).getAllLocations();
			} else {
				list.add(getOtherLocation(tracker));
			}
		} else { 
			for (int i = 0; i <= roadSegments.length - 1; i++) {
				if (roadSegments[i].getClass() == Route.class) {
					ArrayList<Location> sublist = new ArrayList<Location>();
					sublist.addAll(Arrays.asList(((Route) roadSegments[0]).getAllLocations()));
					sublist.remove(0);
					list.addAll(sublist);
				} else {
					tracker = ((Segments) roadSegments[i]).getOtherLocation(tracker);
					list.add(tracker);
				}
			}
		}
		Location[] array = new Location[list.size()];
		list.toArray(array);
		return array;
	}

	/**
	 * Returns the end location of this route
	 */
	public Location getEndLocation(){
		return endLocation;
	}
	/**
	 * This method overrides the toString representation of an instance of the road class
	 *
	 * @return Returns a string stating "This route has the following properties, It connects these segments:" then listing
	 * each road segment in the array of road segments, then stating "with these respective locations" listing each location
	 * visited while traveling along the route. Finally returning a string saying "Blocked in the direction of the route:"
	 * stating the current status of the route and whether it is traversable or not.
	 */
	@Override
	public String toString() {
		String string1 = new String("This route has the following properties:"+"\n"+"It connects these segments: ");
		String segments = ((Road) roadSegments[0]).getID();
		for(int i = 1; i < roadSegments.length; i++) {
			segments = segments + ", "+((Road) roadSegments[i]).getID();
		}	
		String string2 = new String("\n"+"With these respective locations: ");
		String locations = getAllLocations()[0].getAddress();
		for(int i = 1; i < getAllLocations().length; i++) {
			locations = locations + ", "+getAllLocations()[i].getAddress();
		}
		return string1+segments+string2+locations+"\n"+"Blocked in the direction of the route: "+!isTraversable()+"\n";
	}


}
