package com.example.cmpt276_a3.ca.cmpt276A3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Singleton class
 */
public class MineManager {
    private int rows;
    private int columns;
    private int count;
    private Mine[][] mines;

    private MineManager() {
        /**
         * Prevent anyone else from accessing
         */
    }

    /**
     * Singleton support
     */
    private static MineManager instance;

    public static MineManager getInstance() {
        if(instance == null) {
            instance = new MineManager();
        }
        return instance;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void populateMines() {
        System.out.println("\n\n" + this.getRows() + "," + this.getColumns() + "  Count = " + this.getCount() + "\n\n");
        mines = new Mine[rows][columns];
        ArrayList<Integer> randIndexes = new ArrayList<>();

        for(int i=0; i<rows*columns; i++)
        {
            randIndexes.add(new Integer(i));
        }
        Collections.shuffle(randIndexes);
        for(int index: randIndexes) {
            System.out.println(index);
        }

        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                int k=0;
                boolean isDone = false;
                for(int index: randIndexes) {
                    if(k<count && isDone==false) {
                        if (index == (i * columns) + j) {
                            System.out.println("Index matched.");
                            mines[i][j] = new Mine(false, true);
                            isDone = true;
                        } else
                            mines[i][j] = new Mine(false, false);
                        k++;
                    }
                    else if(isDone == false)
                        mines[i][j] = new Mine(false, false);
                }
            }
        }
        printMines();
    }

    private void printMines() {
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                System.out.println("Is revealed:" + getMines(i,j).isRevealed() + ", is Present: " + getMines(i,j).isPresent());
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCount() {
        return count;
    }

    public Mine getMines(int x, int y) {
        System.out.println("\n\nIn mine manager class: " + this.getRows() + "," + this.getColumns() + "  Count = " + this.getCount() + "\n\n");
        System.out.println("x = " + x + " and Y = " + y);
        return mines[x][y];
    }
}
