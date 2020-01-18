package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ShoppingListButton implements View.OnClickListener {
  private Context appContext;

  public ShoppingListButton(Context context){
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, ShoppingListActivity.class);
    appContext.startActivity(intent);
  }
}
