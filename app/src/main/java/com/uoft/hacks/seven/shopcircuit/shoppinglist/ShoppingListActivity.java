package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.uoft.hacks.seven.shopcircuit.R;
import com.uoft.hacks.seven.shopcircuit.map.MapButtonController;
import java.util.ArrayList;

public class ShoppingListActivity extends ListActivity {
  private Button mapButton;
  private Button shoppingListButton;

  private ShoppingList shoppingList;
  Item item;
  private ArrayList<String> listItems = new ArrayList<>();
  private ArrayList<String> selected = new ArrayList<>();
  private ArrayAdapter<String> adapter;
  private ArrayAdapter<String> itemAdapter;
  private ArrayAdapter<String> categoryAdapter;

  private AutoCompleteTextView actvItem;
  private AutoCompleteTextView actvCategory;
  CheckedTextView cView;

  String[] items = {"Apple", "Banana", "TV", "Laptop", "Chair", "Table", "Tshirt", "Pants", "Toothbrush", "Toothpaste", "Steak", "Fish"};
  //change to items we have in db
  String[] categories ={"Fruits", "Meats", "Electronics", "Furniture", "Toiletries" };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_list_layout);

    mapButton = findViewById(R.id.shopListToMapButton);
    mapButton.setOnClickListener(new MapButtonController(this));
//    shoppingListButton = findViewById(R.id.shoppingListButtonNothing);
//    shoppingListButton.setBackgroundColor(R.color.darkGrey);

    shoppingList = new ShoppingList();

    adapter = new ArrayAdapter(this, R.layout.item, listItems);
    setListAdapter(adapter);

    itemAdapter = new ArrayAdapter(this, R.layout.autocomplete, items);
    categoryAdapter = new ArrayAdapter(this, R.layout.autocomplete, categories);

    actvItem = findViewById(R.id.autoCompleteItem);
    actvItem.setThreshold(1);
    actvItem.setAdapter(itemAdapter);

    actvCategory = findViewById(R.id.autoCompleteCategory);
    actvCategory.setThreshold(1);
    actvCategory.setAdapter(categoryAdapter);
  }

  public void addItems(View v){
    String itemName = actvItem.getText().toString();
    String category = actvCategory.getText().toString();
    if (itemName.equals("") || category.equals("")){
      return;
    }

    item = new Item(itemName, category);

    if (listItems.contains(item.getName() + " : " + item.getCategory())){
      Toast.makeText(this, "Item is already in list", Toast.LENGTH_LONG).show();
      return;
    }
    //sql stuff idk
    listItems.add(item.getName() + " : " + item.getCategory());
    shoppingList.addItemToList(item);

    adapter.notifyDataSetChanged();
    actvItem.setText("");
    actvCategory.setText("");
    actvItem.requestFocus();
  }

  public void clear(View v){
    listItems.clear();
    adapter.notifyDataSetChanged();
  }

  public void delete(View v) {
    int colon;
    for (String selectedItem : selected) {
      listItems.remove(selectedItem);
      colon = selectedItem.indexOf(":");
      item = new Item(selectedItem.substring(0, colon -1), selectedItem.substring(colon +1));
      shoppingList.removeItemFromList(item);
    }
    selected.clear();
    cView.setChecked(false);
    adapter.notifyDataSetChanged();
  }

  public void toggle(View v){
    cView = v.findViewById(R.id.checkedTextView);
    if(cView.isChecked()){
      cView.setChecked(false);
      for (String item : selected){
        if (cView.getText().toString().equals(item)){
          selected.remove(item);
          break;
        }
      }
    }
    else{
      cView.setChecked(true);
      selected.add(cView.getText().toString());
    }
  }

  @Override
  public void onBackPressed() {

  }
}

