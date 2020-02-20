package com.example.cmpt276_a3.ca.cmpt276A3.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MineManager {
    private int rows = 4;
    private int columns = 6;
    private int count = 10;
    private Mine[][] mines;

    public MineManager(int rows, int columns, int count) {
        this.count = count;
        this.rows = rows;
        this.columns = columns;
        populateMines();
    }

    public MineManager() {
        /**
         * Prevent anyone else from accessing
         */
        populateMines();
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

        int k=0;
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                for(int index: randIndexes) {
                    if(k<10 && index == (i*columns + j)) {
                        mines[i][j] = new Mine(false, true);
                    }
                    else
                        mines[i][j] = new Mine(false, false);
                }
            }
        }
    }

    // public void add(Mine mine) {mines.add(mine);}


    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getCount() {
        return count;
    }

    private static MineManager instance;

    public static MineManager getInstance() {
        if(instance == null) {
            instance = new MineManager();
        }
        return instance;
    }

    public Mine getMines(int i, int j) {
        return mines[i][j];
    }
}
