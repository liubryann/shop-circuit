package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import android.view.View;
import android.widget.CheckedTextView;

public class CheckBoxButton implements View.OnClickListener {

  public CheckBoxButton() {
  }

  @Override
  public void onClick(View v) {
    ((CheckedTextView)v).toggle();
  }


}
