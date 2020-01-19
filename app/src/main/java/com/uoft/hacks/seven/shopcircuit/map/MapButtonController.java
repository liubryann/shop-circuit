package com.uoft.hacks.seven.shopcircuit.map;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MapButtonController implements View.OnClickListener {
  private Context appContext;

  public MapButtonController(Context context){
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, MapActivity.class);
    appContext.startActivity(intent);
  }
}
