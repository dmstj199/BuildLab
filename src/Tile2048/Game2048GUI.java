// Game2048GUI.java
package Tile2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Game2048GUI extends JFrame {
    private final Game2048 game;
    private final BoardPanel boardPanel;

    public Game2048GUI() {
        game = new Game2048();
        boardPanel = new BoardPanel(game.getBoard());

        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(340, 400));
        setLayout(new BorderLayout());

        add(boardPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(game.isGameOver()){
                    JOptionPane.showMessageDialog(null, "Game Over!", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        game.move(0);
                        break;
                    case KeyEvent.VK_DOWN:
                        game.move(1);
                        break;
                    case KeyEvent.VK_LEFT:
                        game.move(2);
                        break;
                    case KeyEvent.VK_RIGHT:
                        game.move(3);
                        break;
                }
                boardPanel.updateBoard(game.getBoard());
                repaint();
            }
        });
    }

    class BoardPanel extends JPanel {
        private int[][] board;

        public BoardPanel(int[][] initialBoard) {
            this.board = initialBoard;
            setPreferredSize(new Dimension(340, 400));
            setBackground(new Color(0xbbada0));
        }

        public void updateBoard(int[][] newBoard) {
            this.board = newBoard;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    int x = col * (BoardPanel.this.getWidth() / board.length);
                    int y = row * (BoardPanel.this.getHeight() / board.length);
                    drawTile(g, board[row][col], x, y);
                }
            }
        }

        private void drawTile(Graphics g, int value, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int tileSize = this.getWidth() / board.length - 2;
            Color background = getBackground(value);
            g2d.setColor(background);
            g2d.fillRect(x + 1, y + 1, tileSize, tileSize);

            if (value != 0) {
                drawNumber(g2d, value, x + 1, y + 1, tileSize);
            }
            g2d.dispose();
        }

        private void drawNumber(Graphics2D g, int value, int x, int y, int tileSize) {
            String text = String.valueOf(value);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            FontMetrics fm = g.getFontMetrics();
            int ascent = fm.getAscent();
            int descent = fm.getDescent();
            int xText = x + (tileSize - fm.stringWidth(text)) / 2;
            int yText = y + (ascent + (tileSize - (ascent + descent)) / 2);

            Color foreground = value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
            g.setColor(foreground);
            g.drawString(text, xText, yText);
        }

        private Color getBackground(int value) {
            switch (value) {
                case 2:
                    return new Color(0xeee4da);
                case 4:
                    return new Color(0xede0c8);
                case 8:
                    return new Color(0xf2b179);
                case 16:
                    return new Color(0xf59563);
                case 32:
                    return new Color(0xf67c5f);
                case 64:
                    return new Color(0xf65e3b);
                case 128:
                    return new Color(0xedcf72);
                case 256:
                    return new Color(0xedcc61);
                case 512:
                    return new Color(0xedc850);
                case 1024:
                    return new Color(0xedc53f);
                case 2048:
                    return new Color(0xedc22e);
                default:
                    return new Color(0xcdc1b4);
            }
        }
    }

    public static void main(String[] args) {
        new Game2048GUI();
    }
}
