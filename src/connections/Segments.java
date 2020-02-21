package connections;

/**
 * A class of segments. Each segment is either a subclass of Road(One way, two way or alternating) or a Route.
 *
 * @author Michiel Van der Haegen
 * @author Sam Haberman
 */

public abstract class Segments {

	/**
	 * Returns all valid start locations for this road
	 */
	public abstract Location[] getStartLocations();
	
	/**
	 * Returns all valid end locations for this road
	 */
	public abstract Location[] getEndLocations();

	/**
	 * Returns the length of the segment
	 */
	public abstract int getLength();

	/**
	 * Returns the other location of the road when given a location
	 */
	public abstract Location getOtherLocation(Location location);

	/**
	 * Returns a array of all the segments for a Route
	 */
	public abstract Object[] getRouteSegments();

	/**
	 * Checks to see whether an object contains itself in one of its segments.
	 * @param segment The segment to check
	 */
	public abstract boolean containsItself(Object segment);

	/**
	 * The start location of this segment.
	 */
	protected Location startLocation;

	/**
	 * The end location of this segment.
	 */
	protected Location endLocation;
	

	public static void main(String[] args) {
		
// 	THE CODE FOR THE MAIN METHOD OF PART 1
		
//	double[] point1 = new double[] {10.0,20.0};
//	double[] point2 = new double[] {15.3,60.6};
//	double[] point3 = new double[] {40.5,5.5};
//	double[] point4 = new double[] {3.14,42.0};
//
//	Road road = new Road("E40",point1,point2,1000,120.0F,100.0F);
//	Road road2 = new Road("E17",point2,point3,200,120.0F,50.0F);
//	Road road3 = new Road("R0",point3,point4,500,120.0F,25.0F);
//	System.out.println(road);
//	System.out.println(road2);
//	System.out.println(road3);
//
//	road.setDelayDirectionEndPointTwo(100);
//	System.out.println("A delay of 100 seconds was set in the forward direction of "+road.getID());
//	road.setBlockedDirectionEndPointOne(true);
//	System.out.println("The "+road.getID()+" was blocked in the backwards direction");
//	float totalTime = road.calculateTravelTimeEndPointTwo()+road2.calculateTravelTimeEndPointTwo()+road3.calculateTravelTimeEndPointTwo();
//	System.out.println("The total travel time is: "+totalTime+" seconds."+'\n');
//
//	road2.setDelayDirectionEndPointTwo(Float.POSITIVE_INFINITY);
//	System.out.println("A delay of infinity was set in the forward direction of "+road2.getID());
//	float totalTime2 = road.calculateTravelTimeEndPointTwo()+road2.calculateTravelTimeEndPointTwo()+road3.calculateTravelTimeEndPointTwo();
//	System.out.println("The total travel time is: "+totalTime2+" seconds."+'\n');
//
//	System.out.println("This is a test for ourselves: if the road is blocked, does it return infinity?");
//	float totalTime3 = road.calculateTravelTimeEndPointOne()+road2.calculateTravelTimeEndPointOne()+road3.calculateTravelTimeEndPointOne();
//	System.out.println("The total travel time is: "+totalTime3+" seconds."+'\n');
//
//   	THE CODE FOR THE MAIN METHOD OF PART 2 (slightly adapted for PART 3)
//
		Location Aarschot = new Location(new double[] {30.0,15.0}, "Aarschot");
		Location Diest = new Location(new double[] {30.0,30.0}, "Diest");
		Location Tienen = new Location(new double[] {10.0,15.0}, "Tienen");
		Location Leuven = new Location(new double[] {40.0,70.0}, "Leuven");

		Road narrowCountryRoad = new One_way("N36", Aarschot, Diest, 200, 8F);
		Road A1 = new Two_way("A1", Diest , Tienen, 300, 25F, 20F);
		Road A2 = new AlternatingRoad("A2", Tienen, Leuven, 250, 30F, 25F);
		Road motorWay = new Two_way("M1", Aarschot, Diest, 400, 40F, 32F);

		System.out.println(narrowCountryRoad+"\n"+motorWay);
		System.out.println("Travel time for narrow road is: "+narrowCountryRoad.calculateTravelTimeEndPointTwo()+"\n"+"Travel time for motorway is: "+motorWay.calculateTravelTimeEndPointTwo()+"\n");

		A2.swapRoadDirection();
		Route route = new Route(Leuven, A2, A1, motorWay);
		System.out.println(route);

		motorWay.setBlockedDirectionEndPointOne(true);
		System.out.println("The motorway was blocked in the direction of the route");
		System.out.println("This route is traversable: "+route.isTraversable());

	}
}
