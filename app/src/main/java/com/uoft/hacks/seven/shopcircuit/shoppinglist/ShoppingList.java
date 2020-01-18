package com.uoft.hacks.seven.shopcircuit.shoppinglist;

import java.util.HashMap;

public class ShoppingList {
  private HashMap<Item, Integer> shoppingList;

  public ShoppingList(){
    shoppingList = new HashMap<>();
  }

  public void addItemToCart(Item item, Integer amount){
    for (Item shoppingItem : shoppingList.keySet()){
      if (shoppingItem.getName().equals(item.getName())){
        Integer oldAmount = shoppingList.get(shoppingItem);
        shoppingList.remove(shoppingItem);
        shoppingList.put(item, oldAmount + amount);
        return;
      }
    }

    //sql stuff?

    shoppingList.put(item, amount);
  }

  public void removeItemFromCart(Item item){
    for (Item shoppingItem : shoppingList.keySet()){
      if(shoppingItem.getName().equals(item.getName())){
        shoppingList.remove(shoppingItem);
      }
    }
  }

  public HashMap<Item, Integer> getShoppingList(){
    return shoppingList;
  }





}
