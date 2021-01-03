// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// Role: Data Wrangler
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import java.util.Date;

/**
 * This is a Flight class, which will be used as edges of the graph and duration of those flights
 * will represent the edge weights.
 * 
 * @author ayushi mishra
 *
 */
public class Flight {

  private String flightName;
  private String flightId;
  private Date departure;
  private Date arrival;
  private int duration;
  private int cost;

  /**
   * constructor that initializes attributes of the Flight
   * 
   * @param flightName
   * @param flightId
   * @param departure
   * @param arrival
   * @param duration
   * @param cost
   */
  public Flight(String flightName, String flightId, Date departure, Date arrival, int duration,
      int cost) {
    this.flightName = flightName;
    this.flightId = flightId;
    this.departure = departure;
    this.arrival = arrival;
    this.duration = duration;
    this.cost = cost;
  }

  @Override
  public String toString() {
    return ("Flight " + flightName + " with flight number : " + flightId + " is scheduled from date"
        + departure + "to " + arrival + " in total duration " + duration + " with a cost of $"
        + cost);
  }

  /**
   * getter for flightName
   * 
   * @return name of the specific flight
   */
  public String getFlightName() {
    return this.flightName;
  }

  /**
   * getter for flightId
   * 
   * @return unique Id of the flight
   */
  public String getflightId() {
    return this.flightId;
  }

  /**
   * getter for departure
   * 
   * @return departure date of the flight
   */
  public Date getdepartureDate() {
    return this.departure;
  }

  /**
   * getter for arrival
   * 
   * @return arrival date of the flight
   */
  public Date getarrivalDate() {
    return this.arrival;
  }

  /**
   * getter for duration
   * 
   * @return duration of the flight
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * getter for cost
   * 
   * @return cost of the flight
   */
  public int getCost() {
    return this.cost;
  }

  /**
   * setter for duration
   * 
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }
}
