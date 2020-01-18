package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.uoft.hacks.seven.shopcircuit.R;
import java.util.ArrayList;

public class ShoppingListActivity extends ListActivity {
  private ArrayList<String> listItems = new ArrayList<>();
  private ArrayList<String> selected = new ArrayList<>();
  private ArrayAdapter<String> adapter;
  private int clickCounter = 0;
  CheckedTextView checkBox;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_list_layout);

    adapter = new ArrayAdapter<String>(this, R.layout.item, listItems);
    setListAdapter(adapter);
  }

  public void addItems(View v){
    listItems.add("Clicked : " + clickCounter++);
    adapter.notifyDataSetChanged();
  }

  public void clear(View v){
    listItems.clear();
    adapter.notifyDataSetChanged();
  }

  public void delete(View v){
  //Todo
  }

  public void toggle(View v){
    CheckedTextView cView = v.findViewById(R.id.checkedTextView);
    if(cView.isChecked()){
      cView.setChecked(false);
    }
    else{
      cView.setChecked(true);
    }
  }
}

