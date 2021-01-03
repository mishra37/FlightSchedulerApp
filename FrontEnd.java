// --== CS400 File Header Information ==--
// Name: Uday Malhotra
// Email: umalhotra@wisc.edu
// Team: LB
// Role: Front End Developer
// TA: Divyanshu Saxena
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * This class designs the user interface for the scheduling app.
 * 
 * It allows users to make use of all functionalities of this app in a user-friendly manner.
 * 
 * @author Uday Malhotra
 */
public class FrontEnd {
  // copies the array of airports and flights
  static Airport[] listAirports = new Airport[20];
  static Flight[] listFlights = LoadFlight.flight;
  static int numAirports = 18;
  static Scanner sc = new Scanner(System.in);

  /**
   * Displays menu of commands available to the user.
   * 
   */
  private static void menu() {
    System.out.println("---------------------------------------------------------------");
    System.out.println("Welcome to the Flight Scheduling App.");
    System.out.println("The following commands are at your disposal: ");
    System.out
        .println("Enter 'A' to view a list of airports and their corresponding 3 digit codes.");
    System.out.println("Enter 'S' to begin scheduling a flight");
    System.out.println("Enter 'F' to view a list of flights");
    System.out.println("Enter 'C' to view the list of commands again.");
    System.out.println("Enter 'E' to exit entering commands");
    System.out.println("----------------------------------------------------------------");
  }

  /**
   * This is the driver method that runs repeatedly while the user wishes to use the application.
   * 
   * It calls the different features of thsi application to check routes, view flights, airports,
   * etc.
   * 
   * @param schedule the current FlightScheduler app network being used for the current run.
   */
  public static void beginPrompt(FlightScheduler schedule) {
    makeGraph(schedule);
    Boolean runProgram = true;
    menu(); // prints the menu options
    String input = sc.nextLine();
    while (runProgram) {
      switch (input) {
        case "A":
        case "a":
          viewAirport(schedule);
          break;
        case "S":
        case "s":
          checkRoute(schedule);
          break;
        case "F":
        case "f":
          System.out.println(viewFlight(schedule));
          break;
        case "C":
        case "c":
          menu();
          break;
        case "E":
        case "e":
          runProgram = false;
          break;
        default:
          System.out.println("Please enter a valid command");
          break;
      }

      if (!runProgram)
        break;

      System.out.println("Proceed(Y/N): ");
      input = sc.next();

      if (input.equals("N") || input.equals("n")) {
        break;
      } else if (input.equals("Y") || input.equals("y")) {
        input = sc.nextLine();
        menu();
        System.out.println("Enter another command.");
        input = sc.nextLine();
      }
    }
  }

  /**
   * This method is used to check the best available route open to the user.
   * 
   * It takes an input airport with the desired destination, to return the shortest path of flights
   * available.
   * 
   * @param schedule the current FlightScheduler app network being used for the current run.
   * 
   */
  private static void checkRoute(FlightScheduler schedule) {
    Airport origin = null;
    Airport destination = null;
    boolean invalidOrigin = true;
    while (invalidOrigin) {
      try {
        System.out.println("Please enter the 3 digit code of the airport of origin " + "below: ");
        Long originAirport = sc.nextLong();
        origin = schedule.getAirport(originAirport);
      } catch (Exception e) {
        System.out.println("Please adhere to the right syntax for input");
        continue;
      }
      if (origin == null) {
        System.out.println("This is not a valid airport code.");
        continue;
      }
      invalidOrigin = false;
    }

    boolean invalidDestination = true;
    while (invalidDestination) {
      try {
        System.out.println("Please enter the 3 digit code of the destination airport " + "below: ");
        Long destinationAirport = sc.nextLong();
        destination = schedule.getAirport(destinationAirport);
      } catch (Exception e) {
        System.out.println("Please adhere to the right syntax for input");
        continue;
      }
      if (destination == null) {
        System.out.println("This is not a valid airport code.");
        continue;
      }
      invalidDestination = false;
    }

    System.out
        .println(schedule.quickestRoute(origin, destination, schedule.flightMap));
  }

  /**
   * This method runs through the generated file of flights to display all possible (randomly
   * generated) flights.
   * 
   * @param schedule the current FlightScheduler app network being used for the current run.
   * 
   */
  public static String viewFlight(FlightScheduler schedule) {
    File file = new File("flightList.txt");
    String flightDetails = "";
    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNextLine()) {
        flightDetails += sc.nextLine() + '\n';
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return flightDetails;
  }

  /**
   * This method runs through the generated file of flights to display all possible (randomly
   * generated) flights.
   * 
   * @param schedule the current FlightScheduler app network being used for the current run.
   * 
   */
  public static void viewAirport(FlightScheduler schedule) {
    File file = new File("airportList.txt");
    int idx = 0;
    try (Scanner sc = new Scanner(file)) {
      while (idx != numAirports + 2) {
        System.out.println(sc.nextLine());
        idx++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method creates a randomly generated network of edges and vertices for the current Flight
   * Scheduler object (schedule).
   * 
   * It runs through the array of airports to add them as vertices, followed by random edges of
   * flights between these vertices.
   * 
   * @param schedule the current FlightScheduler app network being used for the current run.
   *
   */
  protected static void makeGraph(FlightScheduler schedule) {
    File file = new File("airportList.txt");
    Scanner scnr = null;
    try {
      scnr = new Scanner(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    int idx = -1;
    String line;
    while (idx != numAirports + 1) {
      line = scnr.nextLine();
      String[] listCommas = line.split(",");
      if (idx == -1) {
        idx++;
        continue;
      }
      Airport newAirObj = new Airport(listCommas[0], Long.parseLong(listCommas[1]), listCommas[2]);
      listAirports[idx] = newAirObj;
      idx++;
    }
    for (int i = 0; i < idx; i++) {
      schedule.addAirport(listAirports[i], schedule.flightMap);
    }
    System.out.println();
    // generating a network of airports connected by flights
    // connecting end to end
    for (int i = 0; i < idx - 1; i++) {
      Airport randOrigin = listAirports[i];
      Airport randDestination = listAirports[i + 1];
      schedule.addFlight(listFlights[i], randOrigin, randDestination, schedule.flightMap);
    }
    // connecting every other node
    for (int i = 0; i < idx - 2; i++) {
      Airport randOrigin = listAirports[i];
      Airport randDestination = listAirports[i + 2];
      // changing the duration by 2
      schedule.addFlight(listFlights[i], randOrigin, randDestination, schedule.flightMap);
    }
  }
}
