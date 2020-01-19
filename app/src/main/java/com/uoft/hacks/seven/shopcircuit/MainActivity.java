package com.uoft.hacks.seven.shopcircuit;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.uoft.hacks.seven.shopcircuit.map.MapButtonController;
import com.uoft.hacks.seven.shopcircuit.shoppinglist.ShoppingListButton;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button shoppingListButton = findViewById(R.id.shoppingListButton);
    shoppingListButton.setOnClickListener(new ShoppingListButton(this));

    Button mapButton = findViewById(R.id.mapButton);
    mapButton.setOnClickListener(new MapButtonController(this));

    ImageView gifView = findViewById(R.id.gifView);
    Glide.with(this).load(R.drawable.shop_circuit_animate).into(gifView);
  }

  @Override
  public void onBackPressed() {
  }
}
