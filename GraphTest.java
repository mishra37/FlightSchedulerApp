// --== CS400 File Header Information ==--
// Name: Ayushi Mishra
// Email: mishra37@wisc.edu
// Team: LB
// TA: Divyanshu Saxena
// Lecturer: Florian
// Notes to Grader: <optional extra notes>
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of Project Three: the
 * implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {

  private CS400Graph<Integer> graph;

  @BeforeEach
  /**
   * Instantiate graph from last week's shortest path activity.
   */
  public void createGraph() {
    graph = new CS400Graph<>();
    // insert vertices 0-9
    for (int i = 0; i < 10; i++)
      graph.insertVertex(i);
    // insert edges from Week 08. Dijkstra's Activity
    graph.insertEdge(0, 2, 1);
    graph.insertEdge(1, 7, 2);
    graph.insertEdge(1, 8, 4);
    graph.insertEdge(2, 4, 4);
    graph.insertEdge(2, 6, 3);
    graph.insertEdge(3, 1, 6);
    graph.insertEdge(3, 7, 2);
    graph.insertEdge(4, 5, 4);
    graph.insertEdge(5, 0, 2);
    graph.insertEdge(5, 1, 4);
    graph.insertEdge(5, 9, 1);
    graph.insertEdge(6, 3, 1);
    graph.insertEdge(7, 0, 3);
    graph.insertEdge(7, 6, 1);
    graph.insertEdge(8, 9, 3);
    graph.insertEdge(9, 4, 5);
  }

  /**
   * Checks the distance/total weight cost from the vertex labeled 0 to 8 (should be 15), and from
   * the vertex labeled 9 to 8 (should be 17).
   */
  @Test
  public void providedTestToCheckPathCosts() {
    assertTrue(graph.getPathCost(0, 8) == 15);
    assertTrue(graph.getPathCost(9, 8) == 17);
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex labeled 0 to 8, and from
   * the vertex labeled 9 to 8.
   */
  @Test
  public void providedTestToCheckPathContents() {
    assertTrue(graph.shortestPath(0, 8).toString().equals("[0, 2, 6, 3, 1, 8]"));
    assertTrue(graph.shortestPath(9, 8).toString().equals("[9, 4, 5, 1, 8]"));
  }

  /**
   * distance/total weight cost from the vertex labeled 3 to 5 (should be 14)
   */
  @Test
  public void TestToCheckPathCost() {
    // tests distance cost from the longest path (3 to 5)
    assertTrue(graph.getPathCost(3, 5) == 14);
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex labeled 3 to 5
   * 
   */
  @Test
  public void TestSequenceOfPath() {
    assertTrue(graph.shortestPath(3, 5).toString().equals("[3, 7, 0, 2, 4, 5]"));
  }

  /**
   * Checks the ordered sequence of data within vertices from the vertex labeled 1 to 5 and 6 10 5
   * 
   */
  @Test
  public void TestAdditionalPathSequence() {
    assertTrue(graph.shortestPath(1, 5).toString().equals("[1, 7, 0, 2, 4, 5]"));

    assertTrue(graph.shortestPath(6, 5).toString().equals("[6, 3, 7, 0, 2, 4, 5]"));
  }

  /**
   * distance/total weight cost from the vertex labeled 1 to 5 (should be 14) and vertex 6 to 5
   * (should be 15)
   */
  @Test
  public void TestAdditionalPathCost() {
    // tests distance cost from the longest path (3 to 5)
    assertTrue(graph.getPathCost(1, 5) == 14);
    assertTrue(graph.getPathCost(6, 5) == 15);
  }

  /**
   * checks if shortestPath works correctly by throwing a NullPointerException when we try to find
   * path for non-existing vertex or non-existing path
   * 
   */
  @Test
  public void TestPathtoNonExistingVertex() {
    boolean check1 = false;
    try {
      // checks for vertex 18 that does not exist
      graph.shortestPath(18, 3);
    } catch (java.util.NoSuchElementException e) {
      check1 = true;
    }
    assertTrue(check1);
    // check for non-existing path
    boolean check2 = false;
    try {
      graph.shortestPath(65, 78);
    } catch (java.util.NoSuchElementException e) {
      check2 = true;
    }
    assertTrue(check2);
  }
}
