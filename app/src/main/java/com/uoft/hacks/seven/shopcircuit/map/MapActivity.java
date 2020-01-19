package com.uoft.hacks.seven.shopcircuit.map;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapActivity extends AppCompatActivity {
  Button mapToShoppingList;
  TableLayout matrix;
  EditText x1, y1, xSize, ySize;
  String[] categories ={"Fruits", "Meats", "Electronics", "Furniture", "Toiletries" };
  List<String> list = Arrays.asList(categories);


  int N = 13;
  private Map map;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_layout);
    map = new Map( N, N);

    x1 = findViewById(R.id.x1);
    y1 = findViewById(R.id.y1);
    xSize = findViewById(R.id.xsize);
    ySize = findViewById(R.id.ysize);
    x1.setTransformationMethod(null);
    y1.setTransformationMethod(null);
    xSize.setTransformationMethod(null);
    ySize.setTransformationMethod(null );


    mapToShoppingList = findViewById(R.id.mapToShoppingListButton);
    mapToShoppingList.setOnClickListener(new ShoppingListButton(this));

    matrix = findViewById(R.id.matrix);
    fillTable(N, map.getGrid(), matrix);
  }

  //-1
  public void addEntrance(View v){
    String sX1 = x1.getText().toString();
    String sY1 = y1.getText().toString();
    String sXSize = xSize.getText().toString();
    String sYSize = ySize.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sX1.equals("") || sY1.equals("") || sXSize.equals("") || sYSize.equals(""))){
      parsedX1 = Integer.parseInt(sX1);
      parsedY1 = Integer.parseInt(sY1);
      parsedXSize = Integer.parseInt(sXSize);
      parsedYSize = Integer.parseInt(sYSize);

    }
    else{
      return;
    }

    map.addDoor(parsedX1, parsedY1, parsedXSize, parsedYSize, false);

    fillTable(N, map.getGrid(), matrix);

    x1.setText("");
    y1.setText("");
    xSize.setText("");
    ySize.setText("");
  }

  //-2
  public void addExit(View v){
    String sX1 = x1.getText().toString();
    String sY1 = y1.getText().toString();
    String sXSize = xSize.getText().toString();
    String sYSize = ySize.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sX1.equals("") || sY1.equals("") || sXSize.equals("") || sYSize.equals(""))){
      parsedX1 = Integer.parseInt(sX1);
      parsedY1 = Integer.parseInt(sY1);
      parsedXSize = Integer.parseInt(sXSize);
      parsedYSize = Integer.parseInt(sYSize);
    }
    else{
      return;
    }

    map.addDoor(parsedX1, parsedY1, parsedXSize, parsedYSize, true);

    fillTable(N, map.getGrid(), matrix);
    x1.setText("");
    y1.setText("");
    xSize.setText("");
    ySize.setText("");
  }

  public void addShelf(View v){
    String sX1 = x1.getText().toString();
    String sY1 = y1.getText().toString();
    String sXSize = xSize.getText().toString();
    String sYSize = ySize.getText().toString();

    int parsedX1, parsedY1, parsedXSize, parsedYSize;

    if (!(sX1.equals("") || sY1.equals("") || sXSize.equals("") || sYSize.equals(""))){
      parsedX1 = Integer.parseInt(sX1);
      parsedY1 = Integer.parseInt(sY1);
      parsedXSize = Integer.parseInt(sXSize);
      parsedYSize = Integer.parseInt(sYSize);

    }
    else{
      return;
    }

    map.addShelf(parsedX1, parsedY1, parsedXSize, parsedYSize, list);

    fillTable(N, map.getGrid(), matrix);

    x1.setText("");
    y1.setText("");
    xSize.setText("");
    ySize.setText("");


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
