package Tile2048;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048GUI extends JFrame {
    private final int[][] tiles;
    private final int tileSize = 100;

    public Game2048GUI(int[][] initialTiles){
        this.tiles = initialTiles;
        setTitle("2048 Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(tileSize * tiles.length, tileSize * tiles[0].length);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                super.keyPressed(e);
            }
        });
//2024.04.30
    }
}
