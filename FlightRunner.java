
public class FlightRunner {
  public static void main(String[] args) {
    LoadFlight fileGen = new LoadFlight();
    fileGen.generateFile();
    System.out.println("Generating files");
    FlightScheduler schedule = new FlightScheduler();
    FrontEnd.beginPrompt(schedule);
  }
}