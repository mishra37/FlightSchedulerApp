// --== CS400 File Header Information ==--
// Name: Vaishnavi Deshpande
// Email: vvdeshpande@wisc.edu
// Team: LB
// TA: Divyanshu Saxena
// Lecturer: Prof. Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This is a tester for the Flight Scheduling Application
 * 
 * It finds the shortest path to a destination based on the duration of the flights
 * 
 * @author Vaishnavi Deshpande
 */

public class FlightTester {
  // the schedule for the flights
  FlightScheduler schedule;
  // the list of added airports
  ArrayList<Airport> listAirport;
  // the list of flights to different airports
  static Flight[] listFlights;

  /**
   * Constructor that initializes the fields and generates the files to be used
   */
  public FlightTester() {
    schedule = new FlightScheduler();
    listAirport = new ArrayList<>();
    LoadFlight fileGen = new LoadFlight();
    fileGen.generateFile();
    listFlights = LoadFlight.flight;
  }

  /**
   * Instantiate graph with airports around the world and the time taken to go from one airport to
   * another
   */
  @BeforeEach
  public void createGraph() {
    // adding airports around the world
    // please refer to the picture testCase.jpg in the folder for an idea of how the graph is
    // structured
    ArrayList<Airport> listAirport = new ArrayList<>();
    Airport toAddNext = new Airport("JFK International Airport", 193, "New York");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));
    toAddNext = new Airport("Chhatrapati Shivaji", 766, "Mumbai");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));
    toAddNext = new Airport("LAX International Airport", 215, "Los Angeles");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));
    toAddNext = new Airport("KIA International Airport", 567, "Bangalore");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));
    toAddNext = new Airport("Changi International Airport", 908, "Singapore");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));
    toAddNext = new Airport("O'Hare International Airport", 333, "Chicago");
    listAirport.add(toAddNext);
    assertTrue(schedule.addAirport(toAddNext, schedule.flightMap));

    // printing how the various airports are connected and their edge weights(duration)
    System.out.println("This is how the graph is structured");
    System.out.println("Origin Code   Destination Code  Duration");
    int testDurationVal = 1;

    // connecting each airport to the next to form a network of flights
    for (int i = 0; i < listAirport.size() - 1; i++) {
      Airport randOrigin = listAirport.get(i);
      Airport randDestination = listAirport.get(i + 1);
      Flight newFlightObj = listFlights[i];
      newFlightObj.setDuration(testDurationVal);
      testDurationVal++;
      System.out.println(randOrigin.getCode() + "                " + randDestination.getCode()
          + "            " + newFlightObj.getDuration());
      assertTrue(schedule.addFlight(newFlightObj, randOrigin, randDestination, schedule.flightMap));
    }

    // connecting every other airport to form a network of flights
    testDurationVal = 1;
    for (int i = 0; i < listAirport.size() - 2; i++) {
      Airport randOrigin = listAirport.get(i);
      Airport randDestination = listAirport.get(i + 2);
      Flight newFlightObj = listFlights[i];
      newFlightObj.setDuration(testDurationVal);
      testDurationVal++;
      System.out.println(randOrigin.getCode() + "                " + randDestination.getCode()
          + "            " + newFlightObj.getDuration());
      assertTrue(schedule.addFlight(newFlightObj, randOrigin, randDestination, schedule.flightMap));
    }
    Flight connectFlight = listFlights[listAirport.size() - 1];
    connectFlight.setDuration(4);
    schedule.addFlight(connectFlight, listAirport.get(listAirport.size() - 1), listAirport.get(0),
        schedule.flightMap);
  }


  /**
   * Checks the cost for a particular schedule(the duration of a journey) incurred by the shortest
   * path
   */
  @Test
  public void testPathTime() {
    System.out.println("Running testPathTime()...");
    // testing the cost for JFK to LA, JFK to Bangalore and Chicago to NYC
    Airport airport1 = new Airport("JFK International Airport", 193, "New York");
    Airport airport2 = new Airport("LAX International Airport", 215, "Los Angeles");
    Airport airport3 = new Airport("KIA International Airport", 567, "Bangalore");
    Airport airport4 = new Airport("O'Hare International Airport", 333, "Chicago");
    boolean flightBool1 = schedule.flightMap.getPathCost(airport1, airport2) == 1;
    boolean flightBool2 = schedule.flightMap.getPathCost(airport1, airport3) == 3;
    boolean flightBool3 = schedule.flightMap.getPathCost(airport4, airport1) == 4;
    if (!flightBool1)
      fail("Test failed for New York to LA");
    if (!flightBool2)
      fail("Test failed for LA to Bangalore");
    if (!flightBool3)
      fail("Test failed for LA to New York");
  }


  /**
   * Checks the shortest ordered sequence/schedule of flights from a source airport to destination
   */
  @Test
  public void testToCheckPathContents() {
    System.out.println("Running testToCheckPathContents()...");
    Airport airport1 = new Airport("Chhatrapati Shivaji", 766, "Mumbai");
    Airport airport4 = new Airport("O'Hare International Airport", 333, "Chicago");

    Airport airport2 = new Airport("JFK International Airport", 193, "New York");
    Airport airport3 = new Airport("Changi International Airport", 908, "Singapore");
    // Testing the shortest path from Mumbai to Chicago
    boolean flightBool1 = schedule.flightMap.shortestPath(airport1, airport4).toString()
        .equals("[Chhatrapati Shivaji with code 766 in Mumbai,"
            + " KIA International Airport with code 567 in Bangalore, "
            + "O'Hare International Airport with code 333 in Chicago]");
    // Testing from NYC to Singapore
    boolean flightBool2 = schedule.flightMap.shortestPath(airport2, airport3).toString()
        .equals("[JFK International Airport with code 193 in New York, "
            + "LAX International Airport with code 215 in Los Angeles, "
            + "Changi International Airport with code 908 in Singapore]");
    if (!flightBool1)
      fail("Test failed for CST to ORD");
    if (!flightBool2)
      fail("Test failed for JFK to CIA");
  }

  /**
   * tests the shortest path to an airport which takes the longest time
   */
  @Test
  public void testMaxLengthShortestPath() {
    // from the approach explained in the Dijkstra shortest path activity, the farthest node through
    // the shortest path is from New York is Chicago and the total duration(cost) is 7
    Airport airport1 = new Airport("JFK International Airport", 193, "New York");
    Airport airport2 = new Airport("O'Hare International Airport", 333, "Chicago");
    boolean flightBool1 = schedule.flightMap.getPathCost(airport1, airport2) == 7;
    if (!flightBool1)
      fail("Test failed for longest Path with max cost");
  }

  /**
   * Checks if the shortestPath method throws a NoSuchElementException if there is no possible path
   * between two airports
   */
  @Test
  public void testNoPossiblePath() {
    boolean testBool1 = false;
    try {
      // Hong Kong International Airport doesn't exist in vertices
      Airport airport1 = new Airport("JFK International Airport", 193, "New York");
      schedule.flightMap
          .shortestPath(new Airport("Hong Kong International Airport", 345, "Hong Kong"), airport1);
    } catch (java.util.NoSuchElementException nsee) {
      testBool1 = true;
    }

    // removes the edge between New York and Mumbai AND also Chicago and New York to see if an
    // exception is thrown when trying to find a path between New York and Mumbai
    Airport airport1 = new Airport("JFK International Airport", 193, "New York");
    Airport airport2 = new Airport("Chhatrapati Shivaji", 766, "Mumbai");
    Airport airport3 = new Airport("O'Hare International Airport", 333, "Chicago");
    schedule.flightMap.removeEdge(airport1, airport2);
    schedule.flightMap.removeEdge(airport3, airport1);
    boolean testBool2 = false;
    try {
      schedule.flightMap.shortestPath(airport1, airport2);
    } catch (java.util.NoSuchElementException nsee) {
      testBool2 = true;
    }

    // Using the removed edges to test if an exception is thrown when trying to find a path between
    // Chicago and New York
    boolean testBool3 = false;
    try {
      System.out.println(schedule.flightMap.shortestPath(airport3, airport1));

    } catch (java.util.NoSuchElementException nsee) {
      testBool3 = true;
    }
    assertTrue(testBool1 && testBool2 && testBool3);
  }
}
