import android.util.Pair;
import java.util.ArrayList;
import java.util.List;

public class Shelf {
  private int x_size;
  private int y_size;
  private int x;
  private int y;
  private List<String> categories;

  Shelf(int x, int y, int x_size, int y_size, List<String> categories){
    this.categories = categories;
    this.x = x;
    this.y = y;
    this.x_size = x_size;
    this.y_size = y_size;
  }

  public void addCategory(String category){
    categories.add(category);
  }

  public List<String> getCategories(){
    return categories;
  }

  public Pair<Integer, Integer> getPosition() {
    return new Pair<>(x, y);
  }

  public Pair<Integer,Integer> getSize(){
    return new Pair<>(x_size,y_size);
  }


}
