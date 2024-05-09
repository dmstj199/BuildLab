package Tile2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Game2048 {
    private static final Logger log = LoggerFactory.getLogger(Game2048.class);
    private final int size = 4;
    private int[][] tiles;
    private static final Random random = new Random();
    private boolean gameOver;

    public Game2048(int size) {
        tiles = new int[size][size];
        addTile();
        addTile();
        gameOver = false;
        log.info("Game init");
    }

    public int[][] getBoard() {
        return tiles;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void move(int direction) {
        if (gameOver) {
            log.info("Game Over");
            return;
        }

        boolean moved = false;
        int[][] newTiles = new int[size][size];

        for (int i = 0; i < size; i++) {
            int[] original = new int[size];
            for (int j = 0; j < size; j++) {
                original[j] = (direction % 2 == 0) ? tiles[i][j] : tiles[j][i];
            }
            int[] merged = mergeTiles(original, direction < 2);
            for (int j = 0; j < size; j++) {
                newTiles[i][j] = (direction % 2 == 0) ? merged[j] : merged[i];
            }
            moved = moved || !java.util.Arrays.equals(original, merged);
        }

        if (moved) {
            tiles = newTiles;
            addTile();
            log.info("Board moved in direction {}", direction);
        }else{
            log.info("No movement", direction);
        }

        if (!canMoveAnywhere()) {
            gameOver = true;
            log.info("Game over!");
        }
    }

    private boolean canMoveAnywhere() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0 ||
                        (j < size - 1 && tiles[i][j] == tiles[i][j + 1]) ||
                        (i < size - 1 && tiles[i][j] == tiles[i + 1][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[] mergeTiles(int[] line, boolean reverse) {
        if (reverse) {
            reverse(line);
        }
        ArrayList<Integer> merged = new ArrayList<>();
        for (int i = 0; i < line.length; i++) {
            if (i < line.length - 1 && line[i] == line[i + 1] && line[i] != 0) {
                merged.add(line[i] * 2);
                i++;  // Skip next tile
            } else if (line[i] != 0) {
                merged.add(line[i]);
            }
        }
        while (merged.size() < size) {
            merged.add(0);
        }
        int[] newLine = merged.stream().mapToInt(Integer::intValue).toArray();
        if (reverse) {
            reverse(newLine);
        }
        return newLine;
    }

    private void reverse(int[] line) {
        for (int i = 0, j = line.length - 1; i < j; i++, j--) {
            int temp = line[i];
            line[i] = line[j];
            line[j] = temp;
        }
    }

    private void addTile() {
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    emptyPositions.add(i * size + j);
                }
            }
        }
        if (!emptyPositions.isEmpty()) {
            int pos = emptyPositions.get(random.nextInt(emptyPositions.size()));
            tiles[pos / size][pos % size] = random.nextFloat() < 0.9 ? 2 : 4;
            log.debug("Added new tile at position {}:{}", pos / size, pos % size);
        }
    }
}