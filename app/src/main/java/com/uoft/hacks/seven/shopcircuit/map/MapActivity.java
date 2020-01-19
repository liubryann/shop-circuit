package com.uoft.hacks.seven.shopcircuit.map;

import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.Item;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingList;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MapActivity extends AppCompatActivity {
  Button mapToShoppingList;
  TableLayout matrix;
  EditText xy, xy2, category;

  int N = 13;
  private Map map;
  private ShoppingList shoppingList;
  ArrayList<Item> itemList;
  List<Shelf> shelves;
  List<Pair<Integer, Integer>> nodes;
  ArrayList<Stack> path = new ArrayList<>();
  Pair<Integer, Integer> dest;
  Stack<Pair<Integer, Integer>>[] shortestPath;
  int grid[][];


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_layout);
    map = new Map( N, N);
    shelves = map.getShelves();
    shoppingList = new ShoppingList();
    itemList = shoppingList.getShoppingList();
    nodes = new ArrayList<>();


    xy = findViewById(R.id.xy);
    xy2 = findViewById(R.id.xy2);
    category = findViewById(R.id.category);


    mapToShoppingList = findViewById(R.id.mapToShoppingListButton);
    mapToShoppingList.setOnClickListener(new ShoppingListButton(this));

    matrix = findViewById(R.id.matrix);
    fillTable(N, map.getGrid(), matrix);
  }

  //-1
  public void addEntrance(View v){
    String sXY = xy.getText().toString();
    String sXY2 = xy2.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sXY.equals("") || sXY2.equals(""))){
      parsedX1 = Integer.parseInt(sXY.substring(0, sXY.indexOf(",")));
      parsedY1 = Integer.parseInt(sXY.substring(sXY.indexOf(",") + 1));
      parsedXSize = Integer.parseInt(sXY2.substring(0, sXY2.indexOf(",")));
      parsedYSize = Integer.parseInt(sXY2.substring(sXY2.indexOf(",")+1));
    }
    else{
      return;
    }

    map.addDoor(parsedX1, parsedY1, parsedXSize, parsedYSize, false);

    fillTable(N, map.getGrid(), matrix);

    xy.setText("");
    xy2.setText("");
    category.setText("");
  }

  //-2
  public void addExit(View v){
    String sXY = xy.getText().toString();
    String sXY2 = xy2.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sXY.equals("") || sXY2.equals(""))){
      parsedX1 = Integer.parseInt(sXY.substring(0, sXY.indexOf(",")));
      parsedY1 = Integer.parseInt(sXY.substring(sXY.indexOf(",") + 1));
      parsedXSize = Integer.parseInt(sXY2.substring(0, sXY2.indexOf(",")));
      parsedYSize = Integer.parseInt(sXY2.substring(sXY2.indexOf(",") + 1));

    }
    else{
      return;
    }

    map.addDoor(parsedX1, parsedY1, parsedXSize, parsedYSize, true);
    dest = new Pair<>(parsedX1, parsedY1);

    fillTable(N, map.getGrid(), matrix);

    xy.setText("");
    xy2.setText("");
    category.setText("");
  }

  public void addShelf(View v) {
    String sXY = xy.getText().toString();
    String sXY2 = xy2.getText().toString();
    String sCategory = category.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sXY.equals("") || sXY2.equals("") || sCategory.equals(""))) {
      parsedX1 = Integer.parseInt(sXY.substring(0, sXY.indexOf(",")));
      parsedY1 = Integer.parseInt(sXY.substring(sXY.indexOf(",")+1));
      parsedXSize = Integer.parseInt(sXY2.substring(0, sXY2.indexOf(",")));
      parsedYSize = Integer.parseInt(sXY2.substring(sXY2.indexOf(",")+1));
    } else {
      return;
    }
    ArrayList<String> list = new ArrayList<>();
    list.add(sCategory);

    map.addShelf(parsedX1, parsedY1, parsedXSize, parsedYSize, list);

    fillTable(N, map.getGrid(), matrix);

    xy.setText("");
    xy2.setText("");
    category.setText("");
  }

  public void findPath(View v){
    grid = map.getGrid();
    for (Item item: itemList){
      for (Shelf shelf : shelves){
        for (String category : shelf.getCategories()){
          if (item.getCategory().equals(category)){
            nodes.add(shelf.getPosition());
          }
        }
      }
    }
//    Stack<Pair<Integer, Integer>>[] pathLocal = new Stack[nodes.size()];
    map.setN(nodes.size());
    map.findPath(dest, map.getStart(), nodes, 0, map.getShortestPath(), 0);

    shortestPath = map.getShortestPath();

    for (int i =0; i < shortestPath.length; i++){
      while (!shortestPath[i].isEmpty()){
        Pair<Integer, Integer> pair = shortestPath[i].pop();
        grid[pair.first][pair.second] = 100;
      }
    }

    fillTable(N, grid, matrix);



//    map.findPath(nodes, 0, path);

  }

  private void fillTable(final int n, final int[][] matrix, TableLayout table) {
    table.removeAllViews();
    for (int i = 0; i < n; i++) {
      TableRow row = new TableRow(this);
      row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

      for (int j = 0; j < n; j++) {
        EditText edit = new EditText(this);
        edit.setPadding(10, 10, 10, 10);
        edit.setBackground(getResources().getDrawable(R.drawable.border));

        edit.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
        edit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
//        uncomment this to display matrix values
//        edit.setText(Double.toString(matrix[i][j]));

        if (matrix[i][j] == -1 || matrix[i][j] == -2){
          edit.setBackground(getResources().getDrawable(R.drawable.entrance));
        }

        if(matrix[i][j] > 0){
          edit.setBackground(getResources().getDrawable(R.drawable.shelf));
        }

        if(matrix[i][j] == 100){
          edit.setBackground(getResources().getDrawable(R.drawable.path));
        }

        edit.setText("     ");

        edit.setKeyListener(null);
        row.addView(edit);
      }
      table.addView(row);
    }
  }

  @Override
  public void onBackPressed() {

  }
}
