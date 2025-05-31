import javax.swing.JFrame;

public class TicTacToe {
    public static void main(String[] args) {
        JFrame frame = new JFrame("〇×ゲーム");
        TicTacToeGraphics grid = new TicTacToeGraphics();
        frame.add(grid);
        frame.setSize(900, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
