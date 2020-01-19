package com.uoft.hacks.seven.shopcircuit.map;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;

public class MapActivity extends AppCompatActivity {
  Button mapToShoppingList;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.map_layout);

    mapToShoppingList = findViewById(R.id.mapToShoppingListButton);
    mapToShoppingList.setOnClickListener(new ShoppingListButton(this));

  }

  @Override
  public void onBackPressed() {

  }
}
