package com.uoft.hacks.seven.shopcircuit.map;

import android.util.Pair;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class Map {
  private static int grid[][] = {};
//      {
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
//          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
//      };
  private static int x;
  private static int y;
  private List<Shelf> shelves = new ArrayList<>();
  private int numShelf;
  private double shortestDistance;
  private Stack<Pair<Integer, Integer>>[] shortestPath;
  Pair<Integer, Integer> start;
  Pair<Integer, Integer> dest;

  Map(int x, int y, int n) {

    this.x = x;
    this.y = y;
    grid = new int[x][y];

    for (int i = 0; i < x; i++) {
      for (int j = 0; j < y; j++) {
        grid[i][j] = 0;
      }
    }

    numShelf = 0;
    shortestDistance = Integer.MAX_VALUE;
    shortestPath = new Stack[n + 1];
  }

  public List<Shelf> getShelves(){
    return shelves;
  }

  public int[][] getGrid(){
    return grid;
  }

  public Stack<Pair<Integer, Integer>>[] getShortestPath(){
    return shortestPath;
  }

  public void addShelf(int x, int y, int x_size, int y_size, List<String> categories){
    Shelf shelf = new Shelf(x, y, x_size, y_size, categories);
    shelves.add(numShelf, shelf);
    numShelf++;

    try{
      for (int i = x; i < x + x_size; i++){
        for (int j = y; j < y+ y_size; j++){
          grid[j][i] = numShelf;
        }
      }
    } catch (Exception e) {
      System.out.println("array index out of bounds");
    }
  }

  public void addDoor(int x, int y, int x_size, int y_size, boolean exit){
    int door = -1;
    if (exit) {
      door = -2;
      dest = new Pair<>(x,y);
    }else{
      start = new Pair<>(x, y);
    }

    try{
      for (int i = x; i < x + x_size; i++){
        for (int j = y; j < y + y_size; j++){
          grid[j][i] = door;
        }
      }
    } catch (Exception e) {
      System.out.println("array index out of bounds");
    }
  }

  private Stack<Pair<Integer, Integer>> aStarSearch(Pair<Integer, Integer> src, Pair<Integer, Integer> dest ){
    Stack<Pair<Integer, Integer>> path = new Stack<>();
    int[][] closedList = new int[x][y];
    Cell[][] cells = new Cell[x][y];

    for (int i = 0; i < x; i++){
      for (int j = 0; j < y; j++){
        cells[i][j] = new Cell(-1, -1, Double.MAX_VALUE, Double.MAX_VALUE);
      }
    }

    int i = src.first, j = src.second;
    cells[i][j].setF(0.0);
    cells[i][j].setG(0.0);
    cells[i][j].setG(0.0);
    cells[i][j].setParent_i(i);
    cells[i][j].setParent_j(j);

    PriorityQueue<Pair<Double, Pair<Integer, Integer>>> openList = new PriorityQueue<>(x * y,
        new Comparator<Pair<Double, Pair<Integer, Integer>>>() {
          @Override
          public int compare(Pair<Double, Pair<Integer, Integer>> o1,
              Pair<Double, Pair<Integer, Integer>> o2) {
            return o1.first.compareTo(o2.first);
          }
        });
    openList.add(new Pair<>(0.0, new Pair<>(i, j)));
    boolean foundDest = false;
    while (!openList.isEmpty()) {
      //System.out.println(Arrays.toString(openList.toArray()));
      Pair<Double, Pair<Integer, Integer>> current = openList.poll();

      i = current.second.first;
      //System.out.println(i);
      j = current.second.second;
      //System.out.println("I: "+ i + " J: " + j);
      closedList[i][j] = 1;
      double gNew, hNew, fNew;
      if (isValid(i - 1, j)) {
        //System.out.println("inside here: 1");
        //System.out.println(closedList[i-1][j]);
        //System.out.println(isUnBlocked(i-1,j));
        //System.out.println("i-1:" + (i-1) + " j: " + j);
        //System.out.println("grid[i-1][j]: "+ grid[i - 1][j]);
        if (isDestination(i - 1, j, dest)) {
          cells[i - 1][j].setParent_i(i);
          cells[i - 1][j].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i - 1][j] == 0 && isUnBlocked(i - 1, j)) {
          //System.out.println(closedList[i-1][j]);
          //System.out.println("inside here: else if");
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i - 1, j, dest);
          fNew = gNew + hNew;
          if (cells[i - 1][j].getF() == Double.MAX_VALUE ||
              cells[i - 1][j].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i - 1, j)));
            cells[i - 1][j].setF(fNew);
            cells[i - 1][j].setG(gNew);
            cells[i - 1][j].setH(hNew);
            cells[i - 1][j].setParent_i(i);
            cells[i - 1][j].setParent_j(j);
          }
        }
      }
      if (isValid(i + 1, j)) {
        //System.out.println("inside here: 1");
        //System.out.println(closedList[i-1][j]);
        //System.out.println(isUnBlocked(i-1,j));
        //System.out.println("i+1:" + (i+1) + " j: " + j);
        //System.out.println("grid[i-1][j]: "+ grid[i + 1][j]);
        if (isDestination(i + 1, j, dest)) {
          cells[i + 1][j].setParent_i(i);
          cells[i + 1][j].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i + 1][j] == 0 && isUnBlocked(i + 1, j)) {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i + 1, j, dest);
          fNew = gNew + hNew;
          if (cells[i + 1][j].getF() == Double.MAX_VALUE || cells[i + 1][j].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i + 1, j)));
            cells[i + 1][j].setF(fNew);
            cells[i + 1][j].setG(gNew);
            cells[i + 1][j].setH(hNew);
            cells[i + 1][j].setParent_i(i);
            cells[i + 1][j].setParent_j(j);
          }
        }
      }
      if (isValid(i, j + 1)) {
        if (isDestination(i, j + 1, dest)) {
          cells[i][j + 1].setParent_i(i);
          cells[i][j + 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i][j + 1] == 0 && isUnBlocked(i, j + 1)) {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i, j + 1, dest);
          fNew = gNew + hNew;
          if (cells[i][j + 1].getF() == Double.MAX_VALUE ||
              cells[i][j + 1].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i, j + 1)));
            cells[i][j + 1].setF(fNew);
            cells[i][j + 1].setG(gNew);
            cells[i][j + 1].setH(hNew);
            cells[i][j + 1].setParent_i(i);
            cells[i][j + 1].setParent_j(j);
          }
        }
      }
      if (isValid(i, j - 1)) {
        if (isDestination(i, j - 1, dest)) {
          cells[i][j - 1].setParent_i(i);
          cells[i][j - 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i][j - 1] == 0 && isUnBlocked(i, j - 1)) {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i, j - 1, dest);
          fNew = gNew + hNew;
          if (cells[i][j - 1].getF() == Double.MAX_VALUE ||
              cells[i][j - 1].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i, j - 1)));
            cells[i][j - 1].setF(fNew);
            cells[i][j - 1].setG(gNew);
            cells[i][j - 1].setH(hNew);
            cells[i][j - 1].setParent_i(i);
            cells[i][j - 1].setParent_j(j);
          }
        }
      }
      if (isValid(i - 1, j + 1)) {
        if (isDestination(i - 1, j + 1, dest)) {
          cells[i - 1][j + 1].setParent_i(i);
          cells[i - 1][j + 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i - 1][j + 1] == 0 && isUnBlocked(i - 1, j + 1)) {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i - 1, j + 1, dest);
          fNew = gNew + hNew;
          if (cells[i - 1][j + 1].getF() == Double.MAX_VALUE ||
              cells[i - 1][j + 1].getF() > fNew) {
            openList.add(new Pair<>(fNew,
                new Pair<>(i - 1, j + 1)));
            cells[i - 1][j + 1].setF(fNew);
            cells[i - 1][j + 1].setG(gNew);
            cells[i - 1][j + 1].setH(hNew);
            cells[i - 1][j + 1].setParent_i(i);
            cells[i - 1][j + 1].setParent_j(j);
          }
        }
      }
      if (isValid(i - 1, j - 1)) {
        if (isDestination(i - 1, j - 1, dest)) {
          cells[i - 1][j - 1].setParent_i(i);
          cells[i - 1][j - 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i - 1][j - 1] == 0 && isUnBlocked(i - 1, j - 1)) {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i - 1, j - 1, dest);
          fNew = gNew + hNew;
          if (cells[i - 1][j - 1].getF() == Double.MAX_VALUE || cells[i - 1][j - 1].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i - 1, j - 1)));
            cells[i - 1][j - 1].setF(fNew);
            cells[i - 1][j - 1].setG(gNew);
            cells[i - 1][j - 1].setH(hNew);
            cells[i - 1][j - 1].setParent_i(i);
            cells[i - 1][j - 1].setParent_j(j);
          }
        }
      }
      if (isValid(i + 1, j + 1)) {
        if (isDestination(i + 1, j + 1, dest)) {
          cells[i + 1][j + 1].setParent_i(i);
          cells[i + 1][j + 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i + 1][j + 1] == 0 &&
            isUnBlocked(i + 1, j + 1)) {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i + 1, j + 1, dest);
          fNew = gNew + hNew;
          if (cells[i + 1][j + 1].getF() == Double.MAX_VALUE ||
              cells[i + 1][j + 1].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i + 1, j + 1)));
            cells[i + 1][j + 1].setF(fNew);
            cells[i + 1][j + 1].setG(gNew);
            cells[i + 1][j + 1].setH(hNew);
            cells[i + 1][j + 1].setParent_i(i);
            cells[i + 1][j + 1].setParent_j(j);
          }
        }
      }
      if (isValid(i + 1, j - 1)) {
        if (isDestination(i + 1, j - 1, dest)) {
          cells[i + 1][j - 1].setParent_i(i);
          cells[i + 1][j - 1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        } else if (closedList[i + 1][j - 1] == 0 && isUnBlocked(i + 1, j - 1)) {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i + 1, j - 1, dest);
          fNew = gNew + hNew;
          if (cells[i + 1][j - 1].getF() == Double.MAX_VALUE ||
              cells[i + 1][j - 1].getF() > fNew) {
            openList.add(new Pair<>(fNew, new Pair<>(i + 1, j - 1)));
            cells[i + 1][j - 1].setF(fNew);
            cells[i + 1][j - 1].setG(gNew);
            cells[i + 1][j - 1].setH(hNew);
            cells[i + 1][j - 1].setParent_i(i);
            cells[i + 1][j - 1].setParent_j(j);
          }
        }
      }
    }
    if (!foundDest) {
      System.out.println("Failed to find the Destination Cell\n");
      return path;
    }
    return path;
  }

  private Stack<Pair<Integer, Integer>> tracePath(Cell[][] cellDetails,
      Pair<Integer, Integer> dest) {
    System.out.println("\nThe Path is ");
    int row = dest.first;
    int col = dest.second;

    Stack<Pair<Integer, Integer>> Path = new Stack<>();
    Stack<Pair<Integer, Integer>> PathCopy = new Stack<>();
    System.out.println("row: " + row + " col: " + col);
    //System.out.println(cellDetails.length);
    //System.out.println(cellDetails[row].length);
    //System.out.println(cellDetails[row][col].getParent_j());
    while (!(cellDetails[row][col].getParent_i() == row
        && cellDetails[row][col].getParent_j() == col)) {
      Path.push(new Pair<>(row, col));
      PathCopy.push(new Pair<>(row, col));
      int temp_row = cellDetails[row][col].getParent_i();
      int temp_col = cellDetails[row][col].getParent_j();
      row = temp_row;
      col = temp_col;
    }

    Path.push(new Pair<>(row, col));
    PathCopy.push(new Pair<>(row, col));
    while (!Path.empty()) {
      Pair<Integer, Integer> p = Path.peek();
      Path.pop();
      System.out.printf("-> (%d,%d) \n", p.first, p.second);
    }
    return PathCopy;
  }

  private boolean isUnBlocked(int row, int col) {
    return grid[row][col] == 0;
  }

  private boolean isDestination(int row, int col, Pair<Integer, Integer> dest) {
    return dest.first == row && dest.second == col;
  }

  private boolean isValid(int i, int j) {
    return i < x && i >= 0 && j < y && j >= 0;
  }

  private double heuristic(int row, int col, Pair<Integer, Integer> dest) {
    return Math
        .sqrt((row - dest.first) * (row - dest.first) + (col - dest.second) * (col - dest.second));
  }

  private double findDistance(Stack<Pair<Integer, Integer>> path) {
    double distance = 0;
    while (!path.empty()) {
      Pair<Integer, Integer> first = path.pop();
      if (!path.empty()) {
        Pair<Integer, Integer> second = path.peek();

        if (first.first.equals(second.first) && !first.second
            .equals(second.second)) {
          distance += 1;
        } else if (!first.first.equals(second.first) && first.second
            .equals(second.second)) {
          distance += 1;
        } else {
          distance += 1.4;
        }
      }
    }
    return distance;
  }

  public Pair<Integer,Integer> getStart(){
    return start;
  }

//  public void findPath(Pair<Integer, Integer> exit, Pair<Integer, Integer> start, List<Pair<Integer, Integer>> nodes,
//      double distance, Stack<Pair<Integer, Integer>>[] path, int depth) {
//
//    if (nodes.isEmpty() && distance < shortestDistance) {
//      shortestDistance = distance + findDistance(aStarSearch(start, exit));
//      path[depth + 1] = aStarSearch(start, exit);
//      shortestPath = path;
//    }
//
//    for (int i = 0; i < nodes.size(); i++) {
//      path[depth] = (aStarSearch(start, nodes.get(i)));
//      List<Pair<Integer, Integer>> pass = new ArrayList<>(nodes);
//      pass.remove(i);
//      System.out.println("this is the size " + nodes.size());
//      if (!nodes.isEmpty()) {
//        findPath(exit, nodes.get(i), pass, distance + findDistance(aStarSearch(start, nodes.get(i))),
//            path, depth);
//      }
//    }
//  }

  public void findPath(Pair<Integer, Integer> exit, Pair<Integer, Integer> start, List<Pair<Integer, Integer>> nodes, double distance, Stack<Pair<Integer, Integer>>[] path, int depth) {

    if (depth == path.length - 1){
      distance = distance + findDistance(aStarSearch(start, exit));
      path[depth] = aStarSearch(start, exit);
    }
    if (nodes.isEmpty() && distance < shortestDistance) {
      shortestDistance = distance + findDistance(aStarSearch(start, exit));
      shortestPath = path;
      //System.out.println("AKASJDFL;AKJSDF;LKAJSD;LF " + path.size());
    }

    for (int i = 0; i < nodes.size(); i++) {
      //System.out.print("This is the call for: ");
      //System.out.print("s_i: " + start.first + " s_j: " + start.second + "towards ====");
      //System.out.println("i: " + nodes.get(i).first + " j: " + nodes.get(i).getSecond());

      path[depth] = (aStarSearch(start, nodes.get(i)));
      List<Pair<Integer, Integer>> pass = new ArrayList<>(nodes);
      pass.remove(i);
      //System.out.println("i: " + nodes.get(i).first + " j: " + nodes.get(i).getSecond());
      System.out.println("this is the size " + nodes.size());
      if (!nodes.isEmpty()) {
        findPath(exit, nodes.get(i), pass, distance + findDistance(aStarSearch(start, nodes.get(i))), path, depth + 1);
      }
    }
  }

//  public void findPath(Pair<Integer, Integer> start, List<Pair<Integer, Integer>> nodes,
//      double distance, Stack<Pair<Integer, Integer>>[] path, int depth) {
//
//    if (nodes.isEmpty() && distance < shortestDistance) {
//      shortestDistance = distance;// + findDistance(aStarSearch(trueStart, trueEnd));
//      shortestPath = path;
//      //System.out.println("AKASJDFL;AKJSDF;LKAJSD;LF " + path.size());
//    }
//
//    for (int i = 0; i < nodes.size(); i++) {
//      //System.out.print("This is the call for: ");
//      //System.out.print("s_i: " + start.first + " s_j: " + start.second + "towards ====");
//      //System.out.println("i: " + nodes.get(i).first + " j: " + nodes.get(i).getSecond());
//
//      path[depth] = (aStarSearch(start, nodes.get(i)));
//      List<Pair<Integer, Integer>> pass = new ArrayList<>(nodes);
//      pass.remove(i);
//      //System.out.println("i: " + nodes.get(i).first + " j: " + nodes.get(i).getSecond());
//      System.out.println("this is the size " + nodes.size());
//      if (!nodes.isEmpty()) {
//        findPath(nodes.get(i), pass, distance + findDistance(aStarSearch(start, nodes.get(i))),
//            path, depth + 1);
//      }
//    }
//  }

//  public void findPath(Pair<Integer, Integer> exit, Pair<Integer, Integer> start, List<Pair<Integer, Integer>> nodes,
////      double distance, Stack<Pair<Integer, Integer>>[] path, int depth) {
////
////    if (depth == shortestPath.length - 1){
////      distance = distance + findDistance(aStarSearch(start, exit));
////      path[depth + 1] = aStarSearch(start, exit);
////    }
////    if (nodes.isEmpty() && distance < shortestDistance) {
////      shortestDistance = distance;
////      shortestPath = path;
////    }
////
////    for (int i = 0; i < nodes.size(); i++) {
////      path[depth] = (aStarSearch(start, nodes.get(i)));
////      List<Pair<Integer, Integer>> pass = new ArrayList<>(nodes);
////      pass.remove(i);
////      System.out.println("this is the size " + nodes.size());
////      if (!nodes.isEmpty()) {
////        findPath(exit, nodes.get(i), pass, distance + findDistance(aStarSearch(start, nodes.get(i))),
////            path, depth + 1);
////      }
////    }
////  }

//  public static void main(String arg[]) {
//    /* Description of the Grid-
//     1--> The cell is not blocked
//     0--> The cell is blocked    */
//
//    for (int i = 0; i < x; i++) {
//      System.out.print(i + " :");
//      for (int j = 0; j < y; j++) {
//        System.out.print(grid[i][j]);
//      }
//      System.out.println();
//
//    }
//    //Pair<Integer, Integer> src = new Pair<>(4,2);
//    //Pair<Integer, Integer> src = new Pair<>(7,0);
//    //Pair<Integer, Integer> dest = new Pair<>(8,0);
//    //Map map = new Map(x,y);
//    //map.shortestPath.add(map.aStarSearch( src, dest));
//    System.out.println("======================================");
//    //Pair<Integer, Integer> src = new Pair<>(8, 0);
//    // Pair<Integer, Integer> dest = new Pair<>(0, 0);
//    //(new Map(x,y)).aStarSearch( src, dest);
//
//    List nodes = new ArrayList<Pair<Integer, Integer>>();
//    nodes.add(new Pair<>(8, 0));
//    nodes.add(new Pair<>(4, 2));
//    nodes.add(new Pair<>(0, 0));
//    Map map = new Map(x, y, nodes.size());
//    Stack<Pair<Integer, Integer>>[] pathLocal = new Stack[nodes.size()];
//    map.findPath(new Pair<>(7, 8), nodes, 0, pathLocal, 0);
//
//    System.out.println(map.shortestPath.length);
//    for (int i = 0; i < map.shortestPath.length; i++) {
//      System.out.println(map.shortestPath[i]);
//    }
//  }
}


