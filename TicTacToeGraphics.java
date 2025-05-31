import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGraphics extends JPanel {

    private String[][] board = new String[3][3]; // 盤面を管理する2D配列
    private int[][][] winPatterns = {
            { { 0, 0 }, { 0, 1 }, { 0, 2 } },
            { { 0, 0 }, { 1, 1 }, { 2, 2 } },
            { { 0, 0 }, { 1, 0 }, { 2, 0 } },
            { { 0, 1 }, { 1, 1 }, { 2, 1 } },
            { { 0, 2 }, { 1, 2 }, { 2, 2 } },
            { { 1, 0 }, { 1, 1 }, { 1, 2 } },
            { { 2, 0 }, { 2, 1 }, { 2, 2 } },
            { { 2, 0 }, { 1, 1 }, { 0, 2 } },
    };

    private String winner = "";

    private boolean gameSet = false;
    private boolean isOTurn = true; // 〇と×のターンを管理

    public TicTacToeGraphics() {
        // 初期状態ではすべて空
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ""; // 空のセル
            }
        }

        // マウスクリックリスナーを追加
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int width = getWidth();
                int height = getHeight() - 100; // 描画領域の調整
                int cellWidth = width / 3;
                int cellHeight = height / 3;

                int row = e.getY() / cellHeight;
                int col = e.getX() / cellWidth;

                if (row < 3 && col < 3 && board[row][col].equals("") && !gameSet) {
                    board[row][col] = isOTurn ? "O" : "X";

                    winner = checkWinner();

                    if (!gameSet) {
                        isOTurn = !isOTurn;
                    }

                    repaint(); // 再描画
                }

            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight() - 100; // 下にスペース

        drawGrid(g, width, height);
        drawMarks(g, width, height); // OやXを描画

        drawTurnMessage(g, width, height); // 下部にターン表示を追加
    }

    public void drawGrid(Graphics g, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));

        int cellWidth = width / 3;
        int cellHeight = height / 3;

        g2.drawLine(cellWidth, 0, cellWidth, height);
        g2.drawLine(2 * cellWidth, 0, 2 * cellWidth, height);
        g2.drawLine(0, cellHeight, width, cellHeight);
        g2.drawLine(0, 2 * cellHeight, width, 2 * cellHeight);
        g2.drawLine(0, 3 * cellHeight, width, 3 * cellHeight);
    }

    public void drawMarks(Graphics g, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g2.setFont(new Font("Arial", Font.BOLD, Math.min(width, height) / 5));

        int cellWidth = width / 3;
        int cellHeight = height / 3;

        FontMetrics fm = g2.getFontMetrics();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String mark = board[i][j];
                if (!mark.equals("")) {
                    int x = j * cellWidth + (cellWidth - fm.stringWidth(mark)) / 2;
                    int y = i * cellHeight + ((cellHeight - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString(mark, x, y);
                }
            }
        }
    }

    private void drawTurnMessage(Graphics g, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        String message = "";
        if (!gameSet) {
            message = isOTurn ? "O のターンです" : "X のターンです";
        } else if ("O".equals(winner) || "X".equals(winner)) {
            message = winner + " の勝ち！";
        } else {
            message = winner;
        }

        g2.setFont(new Font("ゴシック細", Font.BOLD, 50));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        int x = (width - textWidth) / 2;
        int y = height + 50; // グリッドの下（余白内）

        g2.drawString(message, x, y);
    }

    private String checkWinner() {
        int x = 0;
        int y = 0;

        String[] line = new String[3];
        String first = "", second = "", third = "";

        for (int i = 0; i < winPatterns.length; i++) {
            for (int j = 0; j < 3; j++) {
                x = winPatterns[i][j][0];
                y = winPatterns[i][j][1];
                line[j] = board[x][y];
            }

            first = line[0];
            second = line[1];
            third = line[2];

            if (!first.equals("") && first.equals(second) && second.equals(third)) {
                gameSet = true;
                return first;

            } else {
                first = "";
                second = "";
                third = "";
            }
        }

        boolean boardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals("")) {
                    boardFull = false;
                    break;
                }
            }
        }

        if (boardFull) {
            gameSet = true;
            winner = "引き分け";
            return winner;
        }

        return "";
    }
}