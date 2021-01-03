// --== CS400 File Header Information ==--
// Name: Ayushi Msihra
// Email: mishra37@wisc.edu
// Team: LB
// Role: Data Wrangler
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.io.FileWriter;
import java.util.Date;

/**
 * This is the class that generates random Flight and Airport objects that can be added to the graph
 * 
 * @author Ayushi Mishra
 */

public class LoadFlight {

  private static Random rand;
  static Flight[] flight;
  static Airport[] airport;
  private Hashtable<Long, Airport> airports = new Hashtable<Long, Airport>();
  // array of random flight names to store initially
  String[] flightName = {"Qatar Airways", "Singapore Airlines", "Emirates", "Lufthansa",
      "Thai Airways", "Qantas Airways", "Hainan Airlines", "EVA Air", "Cathay Pacific Airways",
      "Air New Zealand", "Virgin Atlantic", "Etihad Airways", "Air-India Express",
      "Malaysia Airlines", "American Airlines", "Eastern Airways", "Go Air", "IndiGo", "InterSky",
      "Jet Airways", "Kuwait Airways", "SpiceJet", "Vistara"};
  // array of random airport names to store initially
  String[] airportName = {"JFK International Airport", "Denpasar Airport", "Singapore Airport",
      "Kathmandu Airport", "Kuala Lumpur Airport", "Santa Cruz Airport", "Kuwait Airport",
      "Dubai Airport", "London Airport", "Abu Dhabi Airport", "Munich Airport", "Hong Kong Airport",
      "O'Hare International Airport", "Indira Gandhi International Airport", "Chhatrapati Shivaji ",
      "Maharaj International Airport", "Chennai International Airport",
      "Amsterdam Airport Schiphol", "Los Angeles International Airport",
      "Beijing Capital International Airport"};
  // array of random location to store initially
  String[] location = {"Alaska", "California", "Colorado", "Delaware", "Florida", "Hawaii",
      "Massachusetts", "New York", "Chicago", "North Carolina", "Albany", "Durham", "Lancaster",
      "Manchester", "Liverpool", "Plymouth", "Sydney", "Darwin", "Mumbai", "Chennai"};

  /**
   * this method generates random flights and airport and adds it to an array
   */
  @SuppressWarnings("deprecation")
  public void generateFile() {
    rand = new Random();
    int index = 0;
    flight = new Flight[500];
    airport = new Airport[20];

    
    for (int j = 0; j < flightName.length; j++) {
      int dayOfMonth = rand.nextInt(30) + 1;
      int month = rand.nextInt(12);
      int hour = rand.nextInt(23);
      int min = rand.nextInt(60);
      int second = rand.nextInt(60);
      // departure date object of built-in date class with random date and time
      Date dateDeparture = new Date(2020 - 1900, month, dayOfMonth, hour, min, second);
      // stores a random integer as duration in hour
      int duration = rand.nextInt(23) + 1;
      // arrival date object based on departure date and duration
      Date dateArrival = new Date(dateDeparture.getTime() + duration * 60 * 60 * 1000);

      // flight object is stored in an array
      flight[index] =
          new Flight(flightName[j], Long.toString((long) (Math.random() * 100000000) + 100000000L),
              dateDeparture, dateArrival, duration, (int) (rand.nextFloat() * 100000));
      index++;
    }

    int index2 = 0;
    for (int i = 0; i < airportName.length - 1; i++) {
      long code = (long) ((long) (Math.random() * 100) + 100L);
      // stores code of linked list, to make sure there are no duplicated
      LinkedList<Long> list = new LinkedList<Long>();
      while (list.contains(code)) {
        code = (long) ((long) (Math.random() * 100) + 100L);
      }
      list.add(code);
      
      // airport object is stored in an array
      airport[index2] = new Airport(airportName[i], code, location[i]);
      airports.put(code, airport[index2]);
      index2++;
    }


    // writing to a file 
    try {
      FileWriter fw = new FileWriter("flightList.txt");
      // comma separated file
      fw.write("Flight Name, Flight Id, Departure Date, Arrival Date, Duration, Cost\n");
      for (int i = 0; i < index; i++) {
        fw.write(flight[i].getFlightName() + "," + flight[i].getflightId() + ","
            + flight[i].getdepartureDate() + "," + flight[i].getarrivalDate() + ","
            + flight[i].getDuration() + "," + flight[i].getCost() + "\n");
      }
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    // writing to a file
    try {
      FileWriter fw = new FileWriter("airportList.txt");
      // comma separated file
      fw.write("Airport Name, Airport Code, Location\n");
      for (int i = 0; i < index2; i++) {
        fw.write(airport[i].getAirportName() + "," + airport[i].getCode() + ","
            + airport[i].getLocation() + "\n");
      }
      fw.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /**
   * Gets the airport with associated code
   * 
   * @param code - unique code of Airport
   * @return airport with a unique code
   */
  public Airport getAirport(long code) {
    return (airports.get(code));
  }
}

