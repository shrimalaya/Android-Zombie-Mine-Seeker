package com.example.cmpt276_a3.ca.cmpt276A3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Singleton class
 */
public class MineManager {
    private int rows = 4;
    private int columns = 6;
    private int count = 6;
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
        mines = new Mine[rows][columns];
        ArrayList<Integer> randIndexes = new ArrayList<>();

        for(int i=0; i<rows*columns; i++)
        {
            randIndexes.add(new Integer(i));
        }
        Collections.shuffle(randIndexes);

        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                int k=0;
                boolean isDone = false;
                for(int index: randIndexes) {
                    if(k<count && isDone==false) {
                        if (index == (i * columns) + j) {
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
        return mines[x][y];
    }
}
