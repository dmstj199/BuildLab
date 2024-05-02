package Tile2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game2048 {
    private final int size = 4;
    private int[][] tiles;
    private static final Random random = new Random();
    private boolean gameOver;

    public Game2048() {
        tiles = new int[size][size];
        addTile();
        addTile();
        gameOver = false;
    }

    public int[][] getBoard() {
        return tiles;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void move(int direction) {
        boolean canMove = false;
        int[][] tempTiles = copyTiles();

        // 0: up, 1: down, 2: left, 3: right
        if (direction == 0 || direction == 1) {
            for (int col = 0; col < size; col++) {
                List<Integer> column = new ArrayList<>();
                for (int row = 0; row < size; row++) {
                    if (tiles[row][col] != 0) {
                        column.add(tiles[row][col]);
                    }
                }

                List<Integer> merged = merge(column, direction == 0);
                for (int row = 0; row < size; row++) {
                    tiles[row][col] = row < merged.size() ? merged.get(row) : 0;
                }
            }
        } else {
            for (int row = 0; row < size; row++) {
                List<Integer> line = new ArrayList<>();
                for (int col = 0; col < size; col++) {
                    if (tiles[row][col] != 0) {
                        line.add(tiles[row][col]);
                    }
                }

                List<Integer> merged = merge(line, direction == 2);
                for (int col = 0; col < size; col++) {
                    tiles[row][col] = col < merged.size() ? merged.get(col) : 0;
                }
            }
        }
        if (!canMove) {
            if (!canMoveAnywhere()) {
                gameOver = true; //게임 오버
            }
        }
        addTile();
    }

    private boolean canMoveAnywhere() {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (tiles[i][j] == 0) return true; // 빈 공간이 있으면 움직일 수 있음
                if (j < size - 1 && tiles[i][j] == tiles[i][j + 1]) return true; // 가로 방향 합칠 수 있는 타일 존재
                if (i < size - 1 && tiles[i][j] == tiles[i + 1][j]) return true; // 세로 방향 합칠 수 있는 타일 존재
            }
        }
        return false;
    }

    private int[][] copyTiles(){
        int[][] newTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, newTiles[i], 0, size);
        }
        return newTiles;
    }

    private List<Integer> merge(List<Integer> line, boolean reverse) {
        if (reverse) {
            java.util.Collections.reverse(line);
        }
        List<Integer> merged = new ArrayList<>();
        for (int i = 0; i < line.size(); i++) {
            if (i < line.size() - 1 && line.get(i).equals(line.get(i + 1))) {
                merged.add(line.get(i) * 2);
                i++;
            } else {
                merged.add(line.get(i));
            }
        }
        if (reverse) {
            java.util.Collections.reverse(merged);
        }
        return merged;
    }

    private void addTile() {
        List<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    emptyCells.add(i * size + j);
                }
            }
        }

        int index = emptyCells.get(random.nextInt(emptyCells.size()));
        tiles[index / size][index % size] = random.nextFloat() < 0.9 ? 2 : 4;
    }
}