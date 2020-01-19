import android.util.Pair;

public class Node {
  private Pair<Shelf,Shelf> aisle;
  private static int numNode;
  private int id;

  Node(Shelf shelf1, Shelf shelf2) {
    id = numNode;
    numNode++;
    aisle = new Pair<>(shelf1, shelf2);
  }

  public int getId() {
    return id;
  }

  public Pair<Shelf, Shelf> getAisle() {
    return aisle;
  }

  public static int getNumNode() {
    return numNode;
  }
}
