package Tile2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static Tile2048.Game2048.*;

public class Game2048GUI extends JFrame {
    private final int[][] board; // 게임 보드
    private static final int TILE_SIZE = 100;
    private static final int TILE_MARGIN = 20;

    public Game2048GUI(int[][] initialBoard) {
        this.board = initialBoard;
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(N * TILE_SIZE + (N + 1) * TILE_MARGIN, N * TILE_SIZE + (N + 1) * TILE_MARGIN);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    command = "up";
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    command = "down";
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    command = "left";
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    command = "right";
                }
                sum(); // 게임 로직 실행
                repaint(); // 보드 재그리기
            }
        });

        setVisible(true);
    }

    /* JFream의 paint메서드는 안쓰는게 좋다함(예기치 않은 동작이나 성능 문제가 발생 할 수도 있음
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.BLACK);
        for(int i = 0; i < N; i ++){
            for(int j = 0; j < N;  j++){
                drawTile(g, board[i][j], i, j);
            }
        }
    }
*/
    private class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    drawTile(g, board[i][j], i, j);
                }
            }
        }

        private void drawTile(Graphics g2, int value, int x, int y) {
            Graphics2D g = (Graphics2D) g2;
            //setRenderingHint=>그림 외각선 부드럽게 하기
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int xOffset = offsetCoors(x);
            int yOffset = offsetCoors(y);
            g.setColor(getBackground(value));
            g.fillRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE);
            g.setColor(getForeground(value));
            final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
            final Font font = new Font("Arial", Font.BOLD, size);
            g.setFont(font);

            String s = String.valueOf(value);
            final FontMetrics fm = getFontMetrics(font);

            final int w = fm.stringWidth(s);
            final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

            if (value != 0)
                g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);

            if (command != null) {
                moveTiles();
                command = null; //초기화
            }

        }

        private  int offsetCoors(int arg) {
            return arg * (TILE_MARGIN + TILE_SIZE) + TILE_MARGIN;
        }

        private Color getBackground(int value) {
            return new Color(0xeee4da);
        }

        private Color getForeground(int value) {
            return new Color(0x776e65);
        }

        public static void main(String[] args) {
            int[][] initialMap = new int[N][N]; // 초기 게임 맵 설정
            new Game2048GUI(initialMap);
        }
    }
}
