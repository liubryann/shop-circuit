package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingList {
  private static ArrayList<Item> shoppingList = new ArrayList<>();

  public void addItemToList(Item item){
    shoppingList.add(item);
  }

  public void removeItemFromList(Item item){
    Iterator<Item> iter = shoppingList.iterator();
    Item listItem;
    while (iter.hasNext()) {
      listItem = iter.next();
      if (listItem.getName().equals(item.getName())) {
        iter.remove();
      }
    }
  }

  public ArrayList<Item> getShoppingList(){
    return shoppingList;
  }
}
