package com.example.cmpt276_a3.ca.cmpt276A3.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MineManager {
    private List<Mine> mines = new ArrayList<>();
    private int rows = 4;
    private int columns = 6;
    private int count = 10;

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
    }

    private void populateMines() {
        ArrayList<Integer> randIndexes = new ArrayList<>();
        for(int i=0; i<rows*columns; i++)
        {
            randIndexes.add(new Integer(i));
        }
        Collections.shuffle(randIndexes);

        int j=0;
        for(int i=0; i<rows*columns; i++) {
            //instance.add(new Mine(false, false));
            for(int index: randIndexes) {
                if(j < 10 && index == j) {
                    instance.add(new Mine(false, true));
                }
                else
                    instance.add(new Mine(false, false));
            }
        }
    }

    public void add(Mine mine) {mines.add(mine);}


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

    public Iterator<Mine> iterator() {return mines.iterator();}

    public int getManagerSize() {
        int size = 0;
        for(Mine mine: mines)
            size++;

        return size;
    }

}
