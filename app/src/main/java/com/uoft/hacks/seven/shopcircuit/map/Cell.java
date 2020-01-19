package com.uoft.hacks.seven.shopcircuit.map;

public class Cell {
  private int parent_i, parent_j;
  private double f, g, h;

  Cell(int parent_i, int parent_j, double g, double h){
    this.parent_i = parent_i;
    this.parent_j = parent_j;
    this.f = g + h;
    this.g = g;
    this.h = h;
  }

  public void setF(double f) {
    this.f = f;
  }

  public void setG(double g) {
    this.g = g;
  }

  public void setH(double h) {
    this.h = h;
  }

  public void setParent_i(int parent_i) {
    this.parent_i = parent_i;
  }

  public void setParent_j(int parent_j) {
    this.parent_j = parent_j;
  }

  public double getF() {
    return f;
  }

  public double getG() {
    return g;
  }

  public double getH() {
    return h;
  }

  public int getParent_i() {
    return parent_i;
  }

  public int getParent_j() {
    return parent_j;
  }
}
