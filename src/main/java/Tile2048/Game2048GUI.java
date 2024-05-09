package Tile2048;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Game2048GUI extends JFrame {
    private static final Logger log = LoggerFactory.getLogger(Game2048GUI.class);
    private BoardPanel boardPanel;
    private Game2048 game;

    public Game2048GUI() {
        log.info("init the Game2084 GUI!!!");
        initializeGame();
        setupFrame();
    }

    private void initializeGame() {
        game = new Game2048(4); // 4x4 보드로 게임 초기화
        boardPanel = new BoardPanel(game.getBoard());
        add(boardPanel, BorderLayout.CENTER);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (game.isGameOver()) {
                    log.info("Game Over : restart");
                    return;
                }
                log.info("Key pressed: {}", KeyEvent.getKeyText(e.getKeyCode()));
                int direction = -1;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        direction = 0;
                        break;
                    case KeyEvent.VK_DOWN:
                        direction = 1;
                        break;
                    case KeyEvent.VK_LEFT:
                        direction = 2;
                        break;
                    case KeyEvent.VK_RIGHT:
                        direction = 3;
                        break;
                }
                if (direction != -1) {
                    game.move(direction);
                    boardPanel.updateBoard(game.getBoard());
                    repaint();
                    log.debug("Board updated after move");
                }
            }
        });
    }

    private void setupFrame() {
        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(340, 400));
        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void restartGame() {
        log.info("Restarting the game.");
        remove(boardPanel);
        initializeGame(); // 게임 다시 초기화
        validate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game2048GUI::new);
    }

    private class BoardPanel extends JPanel {
        private int[][] board;

        public BoardPanel(int[][] initialBoard) {
            this.board = initialBoard;
            setPreferredSize(new Dimension(340, 400));
            setBackground(new Color(0xbbada0));
            log.debug("Board panel created.");
        }

        public void updateBoard(int[][] newBoard) {
            this.board = newBoard;
            log.info("Board updated to new state.");
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int row = 0; row < board.length; row++) {
                for (int col = 0; col < board[row].length; col++) {
                    drawTile(g, board[row][col], col, row);
                }
            }
        }

        private void drawTile(Graphics g, int value, int x, int y) {
            log.trace("Drawing tile at [{},{}], value: {}", x, y, value);
            int tileSize = this.getWidth() / board.length;
            int margin = tileSize / 10;
            x = x * tileSize + margin;
            y = y * tileSize + margin;
            tileSize -= 2 * margin;

            Color background = getBackground(value);
            g.setColor(background);
            g.fillRect(x, y, tileSize, tileSize);

            if (value != 0) {
                drawNumber(g, value, x, y, tileSize);
            }
        }

        private void drawNumber(Graphics g, int value, int x, int y, int tileSize) {
            String text = Integer.toString(value);
            Font font = new Font("Arial", Font.BOLD, tileSize / 2);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();

            Color textColor = value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
            g.setColor(textColor);
            g.drawString(text, x + (tileSize - textWidth) / 2, y + (tileSize + textHeight) / 2);
        }

        private Color getBackground(int value) {
            switch (value) {
                case 2:    return new Color(0xeee4da);
                case 4:    return new Color(0xede0c8);
                case 8:    return new Color(0xf2b179);
                case 16:   return new Color(0xf59563);
                case 32:   return new Color(0xf67c5f);
                case 64:   return new Color(0xf65e3b);
                case 128:  return new Color(0xedcf72);
                case 256:  return new Color(0xedcc61);
                case 512:  return new Color(0xedc850);
                case 1024: return new Color(0xedc53f);
                case 2048: return new Color(0xedc22e);
                default:   return new Color(0xcdc1b4);
            }
        }
    }
}
