package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;

public class ShoppingListActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_list_layout);
  }
}

