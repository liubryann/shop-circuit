package com.uoft.hacks.seven.shopcircuit.shoppinglist;

public class Item {
  private String name;
  private String category;

  public Item(String name, String category){
    this.name = name;
    this.category = category;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }
}
