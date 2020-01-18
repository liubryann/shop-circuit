package com.uoft.hacks.seven.shopcircuit;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button shoppingListButton = findViewById(R.id.shoppingListButton);
    shoppingListButton.setOnClickListener(new ShoppingListButton(this));

  }
}
