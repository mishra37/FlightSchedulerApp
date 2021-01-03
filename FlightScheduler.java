// --== CS400 File Header Information ==--
// Name: Rahul S
// Email: sudhakar2@wisc.edu
// Team: LB
// Role: Back End Developer
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.List;

/**
 * 
 * This class indirectly implements the GraphADT interface, by inheriting from the CS400Graph class
 * and its associated methods. It does NOT change any of the inner classes within that class, and
 * can be used with any implementation of the CS400Graph.
 * 
 * @author Rahul S
 *
 */
public class FlightScheduler extends CS400Graph<Airport> {
  protected CS400Graph<Airport> flightMap = new CS400Graph<>(); // flightMap object that vertices
                                                                // and
  // edges are added to.

  /**
   * Adds the airport parameter to the Graph.
   * 
   * @param airport
   * @return true if Airport is added to the Graph, false if airport has already been added, or if
   *         an error is encountered.
   * 
   */
  public boolean addAirport(Airport airport, CS400Graph<Airport> graphObj) {
    try {
      return graphObj.insertVertex(airport);
    } catch (NullPointerException npe) {
      System.out.println(
          "Could not add the airport to the Graph. Please try to add an airport " + "again.");
      return false;
    }
  }

  /**
   * Adds the flight parameter to the edge of the Graph. Since the Edge inner class is not overriden
   * the weight of the edge is the duration of the flight between the origin and destination without
   * explicitly "adding" the flight to the edge of the graph.
   * 
   * @param flight
   * @param origin
   * @param destination
   * @return true if flight is added to the Graph, false if an error is encountered.
   */
  public boolean addFlight(Flight flight, Airport origin, Airport destination,
      CS400Graph<Airport> graphObj) {
    try {
      return graphObj.insertEdge(origin, destination, flight.getDuration());
    } catch (NullPointerException npe) {
      System.out.println(
          "Could not add the flight to the Graph. Please try  to add the flight " + "again.");
      return false;
    }

  }

  /**
   * Returns the airport corresponding to the given airport code. The method iterates through the
   * current list (Graph) of airports added called flightMap and iterates through each of its
   * vertices which are airports.
   * 
   * @param code
   * @return Airport corresponding to the code parameter entered, null if no such Airport is found
   */
  public Airport getAirport(Long code) {
    for (Airport a : flightMap.vertices.keySet()) { // keySet() returns all the keys (Airports)
                                                    // stored
      if (code.equals(a.getCode())) {
        return a;
      }
    }
    return null;
  }

  /**
   * Returns the shortest path between 2 different airports by using Dijkstra's shortest path
   * algorithm. The method converts the list returned to a String representation of the shortest
   * path - with start, intermediate and destination airports.
   * 
   * @param origin      - where the flight begins
   * @param destination - where the flight ends
   * @return path - String representation of the shortest path between the above 2 airports.
   * 
   */
  public String quickestRoute(Airport origin, Airport destination, CS400Graph<Airport> graphObj) {
    List<Airport> shortestPath = graphObj.shortestPath(origin, destination);
    String path = "The shortest possible path starts from: ";
    for (int i = 0; i < shortestPath.size(); i++) {
      if (i == shortestPath.size() - 1) { // destination reached
        path += "Ending at: " + shortestPath.get(i).getAirportName() + "\n";
      } else {
        path += "Through " + shortestPath.get(i).getAirportName() + "\n";
        int eachDuration = 0;
        Vertex sourceVertex = graphObj.vertices.get(shortestPath.get(i));
        Vertex targetVertex = graphObj.vertices.get(shortestPath.get(i + 1));
        for (Edge e : sourceVertex.edgesLeaving)
          if (e.target == targetVertex)
            eachDuration = e.weight;

        String[] allFlights = FrontEnd.viewFlight(this).split("\n");
        for (String eachFlight : allFlights) {
          
          String[] commaSeparatedVal = eachFlight.split(",");
          if (commaSeparatedVal[commaSeparatedVal.length-2].strip().equals(""  + eachDuration)) {
            path += "Flight Details: " + eachFlight + '\n';
            break;
          }
        }
      }
    }
    path +=
        "With a total flying time of: " + flightMap.getPathCost(origin, destination) + " hours \n";
    return path;
  }
}