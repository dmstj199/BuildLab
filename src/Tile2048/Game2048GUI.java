package Tile2048;

import javax.swing.*;
import java.awt.*;
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

        JPanel boardPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                for(int i = 0; i < tiles.length; i++){
                    for (int j = 0; j < tiles[0].length; j++){
                        int x = j*tileSize;
                        int y = i*tileSize;
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(x,y,tileSize, tileSize);

                        String number = tiles[i][j] > 0 ? String.valueOf(tiles[i][j]) : "";
                        g.setColor(Color.BLACK);
                        g.drawString(number, x + tileSize / 2, y + tileSize /2);
                    }
                }
            }
        };
        add(boardPanel); //프레임에 추가

        setVisible(true);
    }

    public static void main(String[] args){
        int[][] initialTiles = {
                {2, 4, 8, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };
        new Game2048GUI(initialTiles);
    }
}
