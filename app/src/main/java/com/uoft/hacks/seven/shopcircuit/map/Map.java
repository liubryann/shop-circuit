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
  private ArrayList<Stack> shortestPath;
  private double shortestDistance;

  Map(int x, int y){

    this.x = x;
    this.y = y;
    grid = new int[x][y];

    for (int i = 0; i < x; i++){
      for (int j = 0; j < y; j++){
        grid[i][j] = 0;
      }
    }

    numShelf = 0;
    shortestDistance = Integer.MAX_VALUE;
  }

  public List<Shelf> getShelves(){
    return shelves;
  }

  public int[][] getGrid(){
    return grid;
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
    while (!openList.isEmpty()){
      Pair<Double, Pair<Integer, Integer>> current = openList.poll();
      i = current.second.first;
      j = current.second.second;
      closedList[i][j] = 1;
      /*
        Generating all the 8 successor of this cell

            N.W   N   N.E
              \   |   /
               \  |  /
            W----com.uoft.hacks.seven.shopcircuit.map.Cell----E
                 / | \
               /   |  \
            S.W    S   S.E

        com.uoft.hacks.seven.shopcircuit.map.Cell-->Popped com.uoft.hacks.seven.shopcircuit.map.Cell (i, j)
        N -->  North       (i-1, j)
        S -->  South       (i+1, j)
        E -->  East        (i, j+1)
        W -->  West           (i, j-1)
        N.E--> North-East  (i-1, j+1)
        N.W--> North-West  (i-1, j-1)
        S.E--> South-East  (i+1, j+1)
        S.W--> South-West  (i+1, j-1)*/
      double gNew, hNew, fNew;
      if (isValid(i-1, j))
      {
        if (isDestination(i-1, j, dest))
        {
          cells[i-1][j].setParent_i(i);
          cells[i-1][j].setParent_j(j);
          path = tracePath (cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i-1][j] == 0 && isUnBlocked(i-1, j))
        {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic (i-1, j, dest);
          fNew = gNew + hNew;
          if (cells[i-1][j].getF() == Double.MAX_VALUE ||
              cells[i-1][j].getF() > fNew)
          {
            openList.add( new Pair<>(fNew,
                new Pair<>(i-1, j)));
            cells[i-1][j].setF(fNew);
            cells[i-1][j].setG(gNew);
            cells[i-1][j].setH(hNew);
            cells[i-1][j].setParent_i(i);
            cells[i-1][j].setParent_j(j);
          }
        }
      }




      if (isValid(i+1, j))
      {


        if (isDestination(i+1, j, dest))
        {

          cells[i+1][j].setParent_i(i);
          cells[i+1][j].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        }



        else if (closedList[i+1][j] == 0 &&
            isUnBlocked(i+1, j) )
        {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i+1, j, dest);
          fNew = gNew + hNew;









          if (cells[i+1][j].getF() == Double.MAX_VALUE || cells[i+1][j].getF() > fNew)
          {
            openList.add( new Pair<> (fNew, new Pair<> (i+1, j)));

            cells[i+1][j].setF(fNew);
            cells[i+1][j].setG(gNew);
            cells[i+1][j].setH(hNew);
            cells[i+1][j].setParent_i(i);
            cells[i+1][j].setParent_j(j);
          }
        }
      }




      if (isValid (i, j+1))
      {


        if (isDestination(i, j+1, dest))
        {

          cells[i][j+1].setParent_i(i);
          cells[i][j+1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        }




        else if (closedList[i][j+1] == 0 && isUnBlocked ( i, j+1))
        {
          gNew = cells[i][j].getG()+ 1.0;
          hNew = heuristic (i, j+1, dest);
          fNew = gNew + hNew;
          if (cells[i][j+1].getF() == Double.MAX_VALUE ||
              cells[i][j+1].getF() > fNew)
          {
            openList.add( new Pair<>(fNew, new Pair<> (i, j+1)));
            cells[i][j+1].setF(fNew);
            cells[i][j+1].setG(gNew);
            cells[i][j+1].setH(hNew);
            cells[i][j+1].setParent_i(i);
            cells[i][j+1].setParent_j(j);
          }
        }
      }




      if (isValid(i, j-1))
      {
        if (isDestination(i, j-1, dest))
        {
          cells[i][j-1].setParent_i(i);
          cells[i][j-1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i][j-1] == 0 &&
            isUnBlocked(i, j-1) )
        {
          gNew = cells[i][j].getG() + 1.0;
          hNew = heuristic(i, j-1, dest);
          fNew = gNew + hNew;
          if (cells[i][j-1].getF() == Double.MAX_VALUE ||
              cells[i][j-1].getF() > fNew)
          {
            openList.add( new Pair<> (fNew, new Pair<> (i, j-1)));


            cells[i][j-1].setF(fNew);
            cells[i][j-1].setG(gNew);
            cells[i][j-1].setH(hNew);
            cells[i][j-1].setParent_i(i);
            cells[i][j-1].setParent_j(j);
          }
        }
      }
      if (isValid(i-1, j+1))
      {
        if (isDestination(i-1, j+1, dest))
        {
          cells[i-1][j+1].setParent_i(i);
          cells[i-1][j+1].setParent_j(j);
          path = tracePath (cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i-1][j+1] == 0 && isUnBlocked(i-1, j+1))
        {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i-1, j+1, dest);
          fNew = gNew + hNew;
          if (cells[i-1][j+1].getF() == Double.MAX_VALUE ||
              cells[i-1][j+1].getF() > fNew)
          {
            openList.add( new Pair<> (fNew,
                new Pair<>(i-1, j+1)));
            cells[i-1][j+1].setF(fNew);
            cells[i-1][j+1].setG(gNew);
            cells[i-1][j+1].setH(hNew);
            cells[i-1][j+1].setParent_i(i);
            cells[i-1][j+1].setParent_j(j);
          }
        }
      }
      if (isValid (i-1, j-1))
      {
        if (isDestination (i-1, j-1, dest))
        {
          cells[i-1][j-1].setParent_i(i);
          cells[i-1][j-1].setParent_j(j);
          path = tracePath (cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i-1][j-1] == 0 && isUnBlocked(i-1, j-1))
        {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i-1, j-1, dest);
          fNew = gNew + hNew;
          if (cells[i-1][j-1].getF() == Double.MAX_VALUE ||
              cells[i-1][j-1].getF() > fNew)
          {
            openList.add( new Pair<> (fNew, new Pair<> (i-1, j-1)));

            cells[i-1][j-1].setF(fNew);
            cells[i-1][j-1].setG(gNew);
            cells[i-1][j-1].setH(hNew);
            cells[i-1][j-1].setParent_i(i);
            cells[i-1][j-1].setParent_j(j);
          }
        }
      }
      if (isValid(i+1, j+1) )
      {
        if (isDestination(i+1, j+1, dest) )
        {
          cells[i+1][j+1].setParent_i(i);
          cells[i+1][j+1].setParent_j(j);
          path = tracePath (cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i+1][j+1] == 0 &&
            isUnBlocked(i+1, j+1) )
        {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i+1, j+1, dest);
          fNew = gNew + hNew;
          if (cells[i+1][j+1].getF() == Double.MAX_VALUE ||
              cells[i+1][j+1].getF() > fNew)
          {
            openList.add(new Pair<>(fNew,
                new Pair<> (i+1, j+1)));
            cells[i+1][j+1].setF(fNew);
            cells[i+1][j+1].setG(gNew);
            cells[i+1][j+1].setH(hNew);
            cells[i+1][j+1].setParent_i(i);
            cells[i+1][j+1].setParent_j(j);
          }
        }
      }
      if (isValid (i+1, j-1))
      {
        if (isDestination(i+1, j-1, dest) )
        {
          cells[i+1][j-1].setParent_i(i);
          cells[i+1][j-1].setParent_j(j);
          path = tracePath(cells, dest);
          foundDest = true;
          break;
        }
        else if (closedList[i+1][j-1] == 0 && isUnBlocked(i+1, j-1))
        {
          gNew = cells[i][j].getG() + 1.414;
          hNew = heuristic(i+1, j-1, dest);
          fNew = gNew + hNew;
          if (cells[i+1][j-1].getF() == Double.MAX_VALUE ||
              cells[i+1][j-1].getF() > fNew)
          {
            openList.add(new Pair<>(fNew,
                new Pair<>(i+1, j-1)));
            cells[i+1][j-1].setF(fNew);
            cells[i+1][j-1].setG(gNew);
            cells[i+1][j-1].setH(hNew);
            cells[i+1][j-1].setParent_i(i);
            cells[i+1][j-1].setParent_j(j);
          }
        }
      }
    }
    if (!foundDest) {
      System.out.println("Failed to find the Destination com.uoft.hacks.seven.shopcircuit.map.Cell\n");
      return path;
    }
    return path;
  }

  private Stack<Pair<Integer, Integer>> tracePath(Cell[][] cellDetails, Pair<Integer, Integer> dest) {
    System.out.println("\nThe Path is ");
    int row = dest.first;
    int col = dest.second;

    Stack<Pair<Integer, Integer>> Path = new Stack<>();
    Stack<Pair<Integer, Integer>> PathCopy = new Stack<>();

    while (!(cellDetails[row][col].getParent_i() == row && cellDetails[row][col].getParent_j() == col ))
    {
      Path.push (new Pair<>(row, col));
      PathCopy.push (new Pair<>(row, col));
      int temp_row = cellDetails[row][col].getParent_i();
      int temp_col = cellDetails[row][col].getParent_j();
      row = temp_row;
      col = temp_col;
    }

    Path.push (new Pair<> (row, col));
    PathCopy.push (new Pair<> (row, col));
    while (!Path.empty())
    {
      Pair<Integer,Integer> p = Path.peek();
      Path.pop();
      System.out.printf("-> (%d,%d) \n", p.first,p.second);
    }
    return PathCopy;
  }

  private boolean isUnBlocked(int row, int col){
    return grid[row][col] == 0;
  }

  private boolean isDestination(int row, int col, Pair<Integer, Integer> dest){
    return dest.first == col && dest.second == row;
  }

  private boolean isValid(int i, int j){
    return i < x && i >= 0 && j < y && j >= 0;
  }

  private double heuristic(int row, int col, Pair<Integer, Integer> dest){
    return Math.sqrt((row-dest.first)*(row-dest.first)+(col-dest.second)*(col-dest.second));
  }

  private double findDistance(Stack<Pair<Integer, Integer>> path) {
    double distance = 0;
    while(!path.empty()){
      Pair<Integer, Integer> first = path.pop();
      Pair<Integer, Integer> second = path.peek();
      if (first.first == second.first && first.second != second.second) {
        distance += 1;
      } else if (first.first != second.first && first.second == second.second) {
        distance += 1;
      } else {
        distance += 1.4;
      }
    }
    return distance;
  }

  public void findPath(List<Pair<Integer, Integer>> nodes, double distance, ArrayList<Stack> path) {

    if (nodes.isEmpty() && distance < shortestDistance) {
      shortestDistance = distance;
      shortestPath = path;
    }

    Pair<Integer, Integer> start = new Pair<Integer, Integer>(0, 0);
    for (int i = 0; i < nodes.size(); i++) {
      distance += findDistance(aStarSearch(start, nodes.get(i)));
      path.add(aStarSearch(start, nodes.get(i)));
      List<Pair<Integer, Integer>> pass = new ArrayList<>(nodes);
      pass.remove(i);
      findPath(pass, distance + findDistance(aStarSearch(start, nodes.get(i))), path);
    }
  }

  public static void main(String arg[])
  {
    /* Description of the Grid-
     1--> The cell is not blocked
     0--> The cell is blocked    */


    Pair<Integer, Integer> src = new Pair<>(8, 0);
    Pair<Integer, Integer> dest = new Pair<>(0, 0);
    (new Map(x,y)).aStarSearch( src, dest);


    ArrayList<Pair<Integer, Integer>> toVisit = new ArrayList<>();



/*
    {
      { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
      { 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 },
      { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
      { 0, 0, 1, 0, 1, 0, 0, 0, 0, 1 },
      { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 },
      { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
      { 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 },
      { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
      { 1, 1, 1, 0, 0, 0, 1, 0, 0, 1 }
    };*/
  }
}


