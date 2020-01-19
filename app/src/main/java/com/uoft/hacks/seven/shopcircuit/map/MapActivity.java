package com.uoft.hacks.seven.shopcircuit.map;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;

public class MapActivity extends AppCompatActivity {
  Button mapToShoppingList;
  TableLayout matrix;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_layout);

    mapToShoppingList = findViewById(R.id.mapToShoppingListButton);
    mapToShoppingList.setOnClickListener(new ShoppingListButton(this));

    matrix = findViewById(R.id.matrix);

    double[][] matrixValues = {{2, 3, 4},{5, 6, 7}, {8, 9, 10}};
    int n = 3;
    fillTable(n, matrixValues, matrix);
  }

  private void fillTable(final int n, final double[][] matrix, TableLayout table) {
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
